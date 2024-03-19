package com.zn.order.service;

import com.zn.core.service.ICrudService;
import com.zn.po.Order;

/**
 * @author 张男
 * @date: 2023/12/24---13:03
 */
public interface OrderService extends ICrudService<Order> {
    Boolean add(Order order);
}
