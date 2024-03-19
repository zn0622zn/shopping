package com.zn.service.impl;

import com.alibaba.fastjson2.JSON;
import com.zn.common.utils.IdWorker;
import com.zn.config.ThreadPool;
import com.zn.core.service.impl.ICrudServiceImpl;
import com.zn.dao.SeckillGoodsDao;
import com.zn.po.SeckillGoods;
import com.zn.po.SeckillOrder;
import com.zn.queue.SeckillQueue;
import com.zn.service.SeckillOrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author 张男
 * @date: 2023/12/25---16:51
 */
@Service
public class SeckillOrderServiceImpl extends ICrudServiceImpl<SeckillOrder> implements SeckillOrderService {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 添加订单
     *
     * @param id   秒杀商品id
     * @param time 秒杀时间段
     * @param uId  用户id
     */
    @Override
    @Async("myPool")
    public void creatOrder() {
        Jedis resource = jedisPool.getResource();
        String seckillQueueStr = resource.rpop("seckill-queue");
        SeckillQueue seckillQueue = JSON.parseObject(seckillQueueStr, SeckillQueue.class);
        //排对信息拉取到了
        if (seckillQueue != null) {
            String uId = seckillQueue.getUId();
            String time = seckillQueue.getTime();
            Long id = seckillQueue.getGoodsId();
            try {
                // redisson实现思想 watchdog
//                if (this.doLock("seckill-lock-" + id)) {
                synchronized (this) {
                    String seckillGoodsStr = resource.hget("SeckillGoods-" + time, String.valueOf(id));
                    SeckillGoods seckillGoods = JSON.parseObject(seckillGoodsStr, SeckillGoods.class);
                    //待秒杀商品没了，或者库存不足
                    if (seckillGoods == null || seckillGoods.getStockCount() <= 0) {
                        return;
                    }
                    //有库存，构建秒杀预订单
                    SeckillOrder seckillOrder = new SeckillOrder();
                    seckillOrder.setId(idWorker.nextId());//预订单id
                    seckillOrder.setSeckillId(id);//秒杀商品id
                    seckillOrder.setMoney(seckillGoods.getCostPrice());//支付金额
                    seckillOrder.setUserId(uId);//用户id
                    seckillOrder.setCreateTime(new Date());//预订单生成时间
                    seckillOrder.setStatus("0");//预订单状态

                    //将秒杀预订单存入redis
                    resource.hset("SeckillOrders-" + uId, id.toString(), JSON.toJSONString(seckillOrder));

                    //5min时间支付
                    ArrayList<String> temp = new ArrayList<>();
                    temp.add(uId);
                    temp.add(id.toString());
                    temp.add(time);
                    rabbitTemplate.convertAndSend("order.direct.exchange", "toSeckillOrder.ttl.key", temp);
                    //判断购买一次后库存-1之后是否还有库存
                    seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
                    //如果这单完成秒杀商品已经无库存了
                    if (seckillGoods.getStockCount() <= 0) {
                        //将商品信息同步回mysql
                        seckillGoodsDao.updateById(seckillGoods);
                        //清空redis缓存中的商品
                        resource.hdel("SeckillGoods-" + time, String.valueOf(id));
                    } else {//如果下完此单，还有库存,秒杀商品信息重置到redis
                        resource.hset("SeckillGoods-" + time, String.valueOf(id), JSON.toJSONString(seckillGoods));
                    }
                }
            } finally {
                this.unlock("seckill-lock-" + id);
                resource.close();
            }
        }
    }

    /**
     * 分布式锁上锁
     *
     * @return
     */
    private boolean doLock(String lockKey) {
        Jedis resource = jedisPool.getResource();
        Long result = resource.setnx(lockKey, "locked");
        System.err.println(result);
        // 设置过期时间，防止锁未被释放时发生死锁
        resource.expire(lockKey, 1);
        //如果结果为1，证明之前无锁，成功的拿到了锁
        //如果结果为0.证明原来有值(即有锁)
        resource.close();
        return result == 1;
    }

    /**
     * 分布式锁解锁
     */
    private void unlock(String lockKey) {
        Jedis resource = jedisPool.getResource();
        resource.del(lockKey);
        resource.close();
    }
}
