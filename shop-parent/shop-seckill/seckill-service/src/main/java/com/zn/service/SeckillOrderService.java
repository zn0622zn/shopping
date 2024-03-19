package com.zn.service;

import com.zn.core.service.ICrudService;
import com.zn.po.SeckillOrder;

/**
 * @author 张男
 * @date: 2023/12/25---16:51
 */
public interface SeckillOrderService extends ICrudService<SeckillOrder> {
    /**
     * 添加秒杀预订单，存在redis中而不是mysql非真正订单
     * @param id:商品ID
     * @param time:商品秒杀开始时间
     * @param username:用户登录名
     * @return
     */
    void creatOrder();
}
