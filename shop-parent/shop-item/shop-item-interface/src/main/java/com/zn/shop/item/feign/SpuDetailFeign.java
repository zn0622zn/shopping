package com.zn.shop.item.feign;

import com.zn.shop.item.po.SpuDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 张男
 * @date: 2023/12/16---15:44
 */
@RequestMapping(value = "/spu-detail")
public interface SpuDetailFeign {
    @GetMapping("/edit/{id}")
    SpuDetail edit(@PathVariable Long id);
}
