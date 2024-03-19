package com.zn.order.controller;

import com.zn.core.controller.BaseController;
import com.zn.core.po.ResponseBean;
import com.zn.order.service.OrderService;
import com.zn.po.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author 张男
 * @date: 2023/12/24---13:12
 */
@RestController
@RequestMapping("/creatOrder")
public class OrderController extends BaseController<OrderService, Order> {

    @PostMapping("/add")
    public ResponseBean add(@RequestHeader("username") String username) throws IOException {
        ResponseBean responseBean = new ResponseBean();
        Order order = new Order();
        order.setUsername(username);
        Boolean add = service.add(order);
        if (add) {
            responseBean.setMessage("订单添加成功");
            return responseBean;
        } else {
            responseBean.setMessage("库存不足，下单失败");
            return responseBean;
        }
    }
}
