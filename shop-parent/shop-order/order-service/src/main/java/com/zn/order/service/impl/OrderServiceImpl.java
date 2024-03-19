package com.zn.order.service.impl;

import com.alibaba.fastjson2.JSON;
import com.zn.common.utils.IdWorker;
import com.zn.core.service.impl.ICrudServiceImpl;
import com.zn.order.client.SkuClient;
import com.zn.order.client.UserClient;
import com.zn.order.dao.OrderItemDao;
import com.zn.order.service.OrderService;
import com.zn.po.Order;
import com.zn.po.OrderItem;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;


@Service
public class OrderServiceImpl extends ICrudServiceImpl<Order> implements OrderService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private UserClient userClient;
    @Autowired
    private SkuClient skuClient;

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public Boolean add(Order order) {
        //1.添加订单表的数据
        order.setId(idWorker.nextId());
        Jedis resource = jedisPool.getResource();
        System.err.println("cart-" + order.getUsername());
        List<String> orderItems = resource.hvals("cart-" + order.getUsername());
        List<OrderItem> orderItemList = new ArrayList<>();
        for (String orderItem : orderItems) {
            orderItemList.add(JSON.parseObject(orderItem, OrderItem.class));
        }
        //购买总数量
        Long totalNum = 0L;
        //购买总金额
        Long totalMoney = 0L;
        //对用户的每一个购物车数据操作
        for (OrderItem orderItem : orderItemList) {
            //购买总数量+
            totalNum += orderItem.getNum();
            //总金额+
            totalMoney += orderItem.getPayMoney();
            //添加订单详情(作为购物车时缺少订单详情id以及外键订单id)
            orderItem.setId(idWorker.nextId());
            orderItem.setOrderId(order.getId());
            //减少库存
            //直接在sql语句中进行判断是否超出库存(数据库行级锁)
            int result = skuClient.decrCount(orderItem.getNum(), orderItem.getSkuId());
            if (result <= 0) {
                throw new RuntimeException("库存为空");
            }
            //添加入订单详情表
            orderItemDao.insert(orderItem);
        }
        //构建order数据
        order.setTotalNum(totalNum);//设置总数量
        order.setTotalMoney(totalMoney);//设置总金额
        order.setCreateTime(new Date());
        order.setOrderStatus("0");//0:未完成
        order.setPayStatus("0");//未支付
        order.setConsignStatus("0");//未发货
        getBaseMapper().insert(order);

        userClient.addPoint(totalNum*10L, order.getUsername());
        //清空当前的用户的redis中的购物车
        resource.del("cart-" + order.getUsername());
        //发送消息给延迟队列，超时未支付，取消订单(根据订单id删除订单，根据username回滚积分，根据订单id删除详情数据，回滚库存)
        HashMap<String, String> map = new HashMap<>();
        map.put("orderMessage", JSON.toJSONString(order));
        rabbitTemplate.convertAndSend("order.direct.exchange", "to.ttl.key", map);
        resource.close();
        return true;
    }

}
