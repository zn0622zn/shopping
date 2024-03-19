package com.zn.login.controlller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zn.core.controller.BaseController;
import com.zn.core.po.ResponseBean;
import com.zn.login.service.UserService;
import com.zn.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

/**
 * @author 张男
 * @date: 2024/1/23---19:51
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/doLogin")
    @ResponseBody
    public ResponseBean login(@RequestParam String username, @RequestParam String password) {
        try {
            System.err.println("登录进入");
            return userService.selectUserByUsername(username, password);
        } catch (Exception e) {
            ResponseBean responseBean = new ResponseBean();
            responseBean.setMessage("登录异常");
            return responseBean;
        }
    }
}