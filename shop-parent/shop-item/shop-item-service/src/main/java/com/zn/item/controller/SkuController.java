package com.zn.item.controller;

import com.zn.core.controller.BaseController;
import com.zn.item.service.SkuService;
import com.zn.shop.item.po.Sku;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/13---9:35
 */
@RestController
@RequestMapping(value = "/sku")
public class SkuController extends BaseController<SkuService, Sku> {
    /**
     * 根据spuId查询对应的所有skus
     */
    @GetMapping("/select-skus-by-spuid/{id}")
    public List<Sku> selectSkusBySpuId(@PathVariable("id") Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        return service.list(sku);
    }

    @PostMapping(value = "/decr-count")
    public int decrCount(@RequestParam("num") Integer num, @RequestParam("skuId") Long skuId) {
        return service.decrCount(num, skuId);
    }
}
