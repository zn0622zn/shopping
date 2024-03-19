package com.zn.item.controller;

import com.zn.core.controller.BaseController;
import com.zn.item.annotation.Manage;
import com.zn.item.service.BrandService;
import com.zn.shop.item.po.Brand;
import com.zn.shop.item.po.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/10---9:37
 */
@RestController
@RequestMapping("/brand")
public class BrandController extends BaseController<BrandService, Brand> {

//    @Override
//    @Manage(value = "MANAGER")
//    public List<Brand> list(Brand entity) {
//        return super.list(entity);
//    }
//
//    @Override
//    @Manage(value = "ADMIN")
//    @GetMapping("/edit/{id}")
//    public Brand edit(@PathVariable Long id) throws Exception {
//        System.out.println(id + "\tbrandController");
//        return super.edit(id);
//    }


    /**
     * 拿到商品id的分类id集合
     */
    @Override
    public void afterEdit(Brand entity) {
        List<Category> categoryIds = service.selectCategoryByBrand(entity.getId());//拿到对应商品的分类信息
        System.err.println(categoryIds);
        Long[] ids = new Long[categoryIds.size()];//商品的分类字段
        for (int i = 0; i < categoryIds.size(); i++) {
            ids[i] = categoryIds.get(i).getId();
        }
        entity.setCategoryIds(ids);
    }

    @GetMapping("/list-by-ids")
    public List<Brand> selectBrandByIds(@RequestParam("ids") List<Long> ids) {
        return service.selectBrandByIds(ids);
    }

    /**
     * 根据分类id查询品牌(门户)
     */
    @GetMapping("/listBrand-ByCid/{cid}")
    public List<Brand> selectBrandByCid(@PathVariable Long cid){
        return service.selectBrandByCid(cid);
    }
}
