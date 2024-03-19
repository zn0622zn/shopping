package com.zn.shop.item.feign;

import com.zn.shop.item.po.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/15---19:19
 */
@RequestMapping("/category")
public interface CategoryFeign {

    @GetMapping("/names")
    List<String> queryNameByIds(@RequestParam("ids") List<Long> ids);

    @RequestMapping(value = "/list")
    List<Category> list(Category category);

    @GetMapping("/edit/{id}")
    Category edit(@PathVariable Long id);
}
