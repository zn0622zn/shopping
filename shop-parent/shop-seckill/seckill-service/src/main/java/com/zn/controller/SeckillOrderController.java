package com.zn.controller;

import com.alibaba.fastjson2.JSON;
import com.zn.core.controller.BaseController;
import com.zn.core.po.ResponseBean;
import com.zn.po.SeckillOrder;
import com.zn.queue.SeckillQueue;
import com.zn.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;

/**
 * @author 张男
 * @date: 2023/12/25---17:00
 */
@RestController
@RequestMapping("/seckill-order")
public class SeckillOrderController extends BaseController<SeckillOrderService, SeckillOrder> {
    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 添加预订单(redis)
     */
    @RequestMapping(value = "/add")
    public ResponseBean add(String uId, String time, Long id) throws InterruptedException {
        ResponseBean responseBean = new ResponseBean();
        Jedis resource = jedisPool.getResource();
        //校验是否重复下单
        String result = resource.hget("Seckill-Orders-" + uId, String.valueOf(id));
        if (result != null) {
            responseBean.setMessage("您已抢购过了");
            resource.close();
            return responseBean;
        }
        //异步创建预订单
        int i = 1;
        while (i < 50) {
            //封装排对信息
            SeckillQueue seckillQueue = new SeckillQueue(String.valueOf(i), new Date(), 1, id, time);
            //从左放入list
            resource.lpush("seckill-queue", JSON.toJSONString(seckillQueue));
            seckillOrderService.creatOrder();
            i++;
//            Thread.sleep(10);
        }
        responseBean.setMessage("处理成功");
        resource.close();
        return responseBean;
    }
}
