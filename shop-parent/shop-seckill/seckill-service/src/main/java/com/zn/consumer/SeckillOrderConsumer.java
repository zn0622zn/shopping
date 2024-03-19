package com.zn.consumer;

import com.alibaba.fastjson2.JSON;
import com.zn.common.utils.IdWorker;
import com.zn.po.SeckillGoods;
import com.zn.po.SeckillOrder;
import com.zn.service.SeckillGoodsService;
import com.zn.service.SeckillOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * @author 张男
 * @date: 2024/2/15---12:41
 */
@Component
public class SeckillOrderConsumer {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private SeckillOrderService seckillOrderService;
    @Autowired
    private SeckillGoodsService seckillGoodsService;
    @Autowired
    private IdWorker idWorker;

    @RabbitListener(queues = "seckillOrder.dead.queue")
    public void doSeckillOrder(@Payload ArrayList<String> arrayList) {
        try {
            Jedis resource = jedisPool.getResource();
            String uId = arrayList.get(0);
            String seckillId = arrayList.get(1);
            String seckillOrderStr = resource.hget("SeckillOrders-" + uId, seckillId);
            SeckillOrder seckillOrder = JSON.parseObject(seckillOrderStr, SeckillOrder.class);
            if (seckillOrder.getStatus().equals("0")) {
                /*
                                未按照规定时间支付
                1.秒杀商品库存回滚
                2.删除redis中的预订单
                 */
                String time = arrayList.get(2);
                String seckillGoodsStr = resource.hget("SeckillGoods-" + time, seckillOrder.getSeckillId().toString());
                System.err.println(seckillGoodsStr);
                SeckillGoods seckillGoods = JSON.parseObject(seckillGoodsStr, SeckillGoods.class);
                System.err.println(seckillGoods);
                if (seckillGoodsStr != null) {//秒杀商品还有库存，重置到redis中
                    seckillGoods.setStockCount(seckillGoods.getStockCount() + 1);
                    resource.hset("SeckillGoods-" + time, seckillOrder.getSeckillId().toString(), JSON.toJSONString(seckillGoods));
                    resource.hdel("SeckillOrders-" + seckillOrder.getUserId(), seckillOrder.getSeckillId().toString());
                } else {
                    //秒杀商品已受控，回滚到数据库秒杀商品库存
                    seckillGoodsService.rollbackCount(Long.valueOf(seckillId));
                    resource.hdel("SeckillOrders-" + uId, seckillId);
                }
                resource.close();
            } else {
                System.err.println("支付ok，下单成功\t，删除预订单，写入真实订单");
                //将秒杀预订单写入mysql，并删除redis中的预订单
                seckillOrder.setPayTime(new Date());
                seckillOrder.setTransactionId(String.valueOf(idWorker.nextId()));
                System.err.println(seckillOrder);
                seckillOrderService.saveOrUpdate(seckillOrder);
                resource.hdel("SeckillOrders-" + seckillOrder.getUserId(), seckillOrder.getSeckillId().toString());
                resource.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

