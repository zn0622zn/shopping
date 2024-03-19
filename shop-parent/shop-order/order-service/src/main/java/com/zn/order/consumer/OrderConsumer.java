package com.zn.order.consumer;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rabbitmq.client.Channel;
import com.zn.order.client.SkuClient;
import com.zn.order.client.UserClient;
import com.zn.order.dao.OrderDao;
import com.zn.order.dao.OrderItemDao;
import com.zn.po.Order;
import com.zn.po.OrderItem;
import com.zn.shop.item.po.Category;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;

/**
 * @author 张男
 * @date: 2024/2/13---15:20
 */
@Component
public class OrderConsumer {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private UserClient userClient;

    @Autowired
    private SkuClient skuClient;

    @RabbitListener(queues = "dead.queue")
    public void doOrder(@Payload HashMap<String, String> map) {
        try {
            Order order = JSON.parseObject(map.get("orderMessage"), Order.class);
            //判断订单支付状态
            String isPay = orderDao.selectPayStatusById(order.getId());
            System.err.println(isPay);
            if (!isPay.equals("1")) {
                //1.回滚库存
                QueryWrapper<OrderItem> queryWrapper1 = Wrappers.query();
                queryWrapper1.eq("order_id_", order.getId());
                List<OrderItem> orderItems = orderItemDao.selectList(queryWrapper1);
                for (OrderItem orderItem : orderItems) {
                    Integer num = orderItem.getNum();
                    skuClient.decrCount(-num, orderItem.getSkuId());
                }

                //2.删除订单详情
                int deleteOrderItemResult = orderItemDao.delete(queryWrapper1);

                //3.删除订单
                QueryWrapper<Order> queryWrapper2 = Wrappers.query();
                queryWrapper2.eq("id_", order.getId());
                int deleteOrderResult = orderDao.delete(queryWrapper2);
                //4.回滚用户积分
                String resultRollbackPoint = userClient.addPoint(-(order.getTotalNum() * 10), order.getUsername());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
