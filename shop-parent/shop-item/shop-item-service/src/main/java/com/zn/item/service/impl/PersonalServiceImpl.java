package com.zn.item.service.impl;

import com.zn.item.dao.CategoryDao;
import com.zn.item.dao.SpuDao;
import com.zn.item.service.PersonalService;
import com.zn.shop.item.po.Category;
import com.zn.shop.item.po.Spu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 张男
 * @date: 2024/2/8---10:48
 */
@Service
public class PersonalServiceImpl implements PersonalService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SpuDao spuDao;


    @Override
    public List<Category> personalCategory(String uId) {
        List<String> categoryId = this.getId("category", uId);
        System.err.println(categoryId);
        return categoryDao.selectCategorySomeCId(categoryId);
    }

    @Override
    public List<Spu> personalBrandSpu(String uId) {
        List<String> brandId = this.getId("brand", uId);
        return spuDao.selectSpuByBrandId(brandId);
    }

    private List<String> getId(String name, String uId) {
        Jedis resource = jedisPool.getResource();
        String s = resource.get("personal-" + name + "-user-" + uId);
        String strip = StringUtils.strip(s, "[]");
        String[] category = strip.split(", ");
        ArrayList<String> list = new ArrayList<>();
        for (String s1 : category) {
            list.add(s1.split("-")[1]);
        }
        resource.close();
        return list;
    }
}
