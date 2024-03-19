package com.zn.order.controller;

import com.zn.order.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 张男
 * @date: 2024/2/13---21:11
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderDao orderDao;

    @RequestMapping("/doPay/{orderId}")
    @ResponseBody
    public String pay(@PathVariable Long orderId) {
        orderDao.selectPayStatusById(orderId);
        int pay = orderDao.pay(orderId);
        if (pay > 0) {
            return "支付成功";
        } else {
            return "请检查是否重复支付";
        }

    }
}
