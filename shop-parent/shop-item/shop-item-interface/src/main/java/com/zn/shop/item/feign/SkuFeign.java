package com.zn.shop.item.feign;

import com.zn.shop.item.po.Sku;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/sku")
public interface SkuFeign {

    @GetMapping("/select-skus-by-spuid/{id}")
    List<Sku> selectSkusBySpuId(@PathVariable("id") Long spuId);

    @GetMapping("/edit/{id}")
    Sku edit(@PathVariable("id") Long id);

    @PostMapping(value = "/decr-count")
    int decrCount(@RequestParam("num") Integer num, @RequestParam("skuId") Long skuId);
}

