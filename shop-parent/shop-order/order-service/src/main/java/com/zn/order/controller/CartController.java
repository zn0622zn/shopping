package com.zn.order.controller;

import com.zn.core.po.ResponseBean;
import com.zn.order.service.CartService;
import com.zn.po.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 购物车接口
 *
 * @author 张男
 * @date: 2023/12/23---17:08
 */
@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * @param id  skuId
     * @param num 购买数量
     * @return
     * @throws IOException
     */
    @RequestMapping("/add/{id}/{num}")
    public ResponseBean add(@RequestHeader("username") String username, @PathVariable("id") Long id, @PathVariable("num") Integer num) throws IOException {
        cartService.add(id, num, username);
        ResponseBean responseBean = new ResponseBean();
        responseBean.setMessage("购物车添加成功");
        return responseBean;
    }

    @RequestMapping("/list")
    public ResponseBean list(@RequestHeader("uId") String uId) throws IOException {
        List<OrderItem> orderItemList = cartService.list(uId);
        ResponseBean responseBean = new ResponseBean();
        responseBean.setObject(orderItemList);
        return responseBean;
    }
}
