package com.zn.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 张男
 * @date: 2024/2/13---13:53
 */
@RequestMapping(value = "/point")
public interface UserFeign {
    @GetMapping(value = "/add-point")
    String addPoint(@RequestParam("point") Long point, @RequestParam("username") String username);
}
