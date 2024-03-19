package com.zn.order.service;


import com.zn.po.OrderItem;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/23---17:01
 */
public interface CartService {
    /**
     * 添加购物车
     * @param id skuId
     * @param num 购买数量
     * @param username 购买该商品的用户名
     */
    void add(Long id, Integer num, String username);


    List<OrderItem> list(String username);
}
