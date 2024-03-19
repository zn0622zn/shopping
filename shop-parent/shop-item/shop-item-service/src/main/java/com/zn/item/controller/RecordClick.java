package com.zn.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;

/**
 * 记录用户商品喜好，首页访问时进行推荐
 *
 * @author 张男
 * @date: 2024/2/7---10:23
 */
@Controller
@RequestMapping("/record")
public class RecordClick {

    private final HttpServletRequest request;

    public RecordClick(HttpServletRequest request) {
        this.request = request;
    }

    @Autowired
    private JedisPool jedisPool;


    /**
     * 记录用户点击某个分类的次数
     */
    @GetMapping("/categoryClick/{uId}/{cId}")
    @ResponseBody
    public String recordCategoryClick(@PathVariable String uId, @PathVariable String cId) {
        //在网关中如果登录过，会将jwt解析拿到userId放入头中，应该从头中拿
        Jedis resource = jedisPool.getResource();
        resource.zincrby("user-category-" + uId, 1, "category-" + cId);
        resource.close();
        return "Y";
    }

    /**
     * 记录某个品牌访问的次数
     */
    @GetMapping("/brandClick/{uId}/{bId}")
    @ResponseBody
    public String recordBrandClick(@PathVariable String uId, @PathVariable String bId) {
        Jedis resource = jedisPool.getResource();
        resource.zincrby("user-brand-" + uId, 1, "brand-" + bId);
        resource.close();
        return "Y";
    }
}
