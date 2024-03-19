package com.zn.pay;

import com.alibaba.fastjson2.JSON;
import com.zn.po.SeckillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author 张男
 * @date: 2024/2/15---12:35
 */
@Controller
@RequestMapping("/pay")
public class SeckillOrderPay {

    @Autowired
    private JedisPool jedisPool;

    @GetMapping("/doPay/{uId}/{seckillId}")
    @ResponseBody
    public String pay(@PathVariable String uId, @PathVariable String seckillId) {
        Jedis resource = jedisPool.getResource();
        String seckillOrderStr = resource.hget("SeckillOrders-" + uId, seckillId);
        if (seckillOrderStr != null) {
            SeckillOrder seckillOrder = JSON.parseObject(seckillOrderStr, SeckillOrder.class);
            seckillOrder.setStatus("1");
            resource.hset("SeckillOrders-" + uId, seckillId, JSON.toJSONString(seckillOrder));
            return "支付成功";
        }else {
            return "支付出现异常";
        }
    }
}
