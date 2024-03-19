package com.zn.item.controller;

import com.zn.item.service.PersonalService;
import com.zn.shop.item.po.Category;
import com.zn.shop.item.po.Spu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用于返回用户个性化分类和品牌以及spu
 *
 * @author 张男
 * @date: 2024/2/8---10:45
 */
@RestController
@RequestMapping("/personal")
public class PersonalController {

    @Autowired
    private PersonalService personalService;

    /**
     * 主页返回推荐的分类
     */
    @RequestMapping("/categoryPersonal/{uId}")
    public List<Category> personalCategory(@PathVariable String uId) {
        return personalService.personalCategory(uId);
    }

    /**
     * 根据个人喜好的推荐和分类，传回独特的SPU
     */
    @RequestMapping("/brandPersonal/{uId}")
    public List<Spu> personalSpuByBrand(@PathVariable String uId) {
        return personalService.personalBrandSpu(uId);
    }
}
