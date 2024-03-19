package com.zn.login.controlller;

import com.zn.login.dao.UserDao;
import com.zn.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 张男
 * @date: 2024/2/13---13:54
 */
@Controller
@RequestMapping("/point")
public class UserPointController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/add-point")
    @ResponseBody
    public String addPoint(@RequestParam("point") Long point, @RequestParam("username") String username) {
        System.err.println(point+"\t"+username);
        userDao.addPoint(point, username);
        return "OK";
    }
}
