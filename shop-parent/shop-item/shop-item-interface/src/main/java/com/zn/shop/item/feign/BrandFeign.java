package com.zn.shop.item.feign;

import com.zn.shop.item.po.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/brand")
public interface BrandFeign {

    @GetMapping("/list-by-ids")
     List<Brand> selectBrandByIds(@RequestParam("ids") List<Long> ids);

}