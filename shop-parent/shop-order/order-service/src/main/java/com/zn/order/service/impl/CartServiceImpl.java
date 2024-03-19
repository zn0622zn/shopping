package com.zn.order.service.impl;

import com.alibaba.fastjson2.JSON;
import com.zn.order.client.SkuClient;
import com.zn.order.client.SpuClient;
import com.zn.order.service.CartService;
import com.zn.po.OrderItem;
import com.zn.shop.item.po.Sku;
import com.zn.shop.item.po.Spu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/23---17:02
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private SkuClient skuClient;

    @Autowired
    private SpuClient spuClient;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Long id, Integer num, String username) {
        //删除逻辑(点击删除按钮，前端传来小于等于0的数，执行对应购物车的删除)
        if (num <= 0) {
            Jedis resource = jedisPool.getResource();
            resource.hdel("cart-" + username, id.toString());
            resource.close();
            return;
        }
        //根据sku的id获取sku数据
        Sku skuDate = skuClient.edit(id);
        if (skuDate != null) {
            Jedis resource = jedisPool.getResource();
            //根据sku的数据对象 获取 该SKU对应的SPU的数据
            Long spuId = skuDate.getSpuId();
            //拿到对应的spu
            Spu spu = spuClient.edit(spuId);
            //构建购物车对象
            OrderItem orderItem = new OrderItem();

            orderItem.setCategoryId1(spu.getCid1());
            orderItem.setCategoryId2(spu.getCid2());
            orderItem.setCategoryId3(spu.getCid3());
            orderItem.setSpuId(spu.getId());
            orderItem.setSkuId(id);
            orderItem.setName(skuDate.getTitle());//商品的名称 sku的名称
            orderItem.setPrice(skuDate.getPrice());//sku的单价
            orderItem.setNum(num);//购买的数量
            //单价* 数量
            orderItem.setPayMoney(orderItem.getNum() * orderItem.getPrice());
            //商品的图片地址
            orderItem.setImage(skuDate.getImages());

            //将信息添加到redis
            resource.hset("cart-" + username, id.toString(), JSON.toJSONString(orderItem));
            //购物车内容七天后自动删除
            resource.expire("cart-" + username, 604800);
            resource.close();
        }
    }

    @Override
    public List<OrderItem> list(String username) {
        Jedis resource = jedisPool.getResource();
        String orderItems = resource.get("cart-" + username);
        List<OrderItem> orderItemsList = JSON.parseArray(orderItems, OrderItem.class);
        resource.close();
        return orderItemsList;
    }
}
