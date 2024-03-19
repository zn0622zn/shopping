package com.zn.shop.item.feign;

import com.zn.shop.item.po.Spu;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/16---15:24
 */
@RequestMapping(value = "/spu")
public interface SpuFeign {
    @GetMapping("/list-all")
    List<Spu> selectAll();

    @GetMapping("/edit/{id}")
    Spu edit(@PathVariable Long id);
}
