package com.zn.order.dao;

import com.zn.core.dao.ICrudDao;
import com.zn.po.Order;

/**
 * @author 张男
 * @date: 2023/12/24---12:52
 */
public interface OrderDao extends ICrudDao<Order> {
    String selectPayStatusById(Long id);

    int pay(Long orderId);
}
