package com.zn.shop.item.feign;

import com.zn.shop.item.po.SpecParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/16---16:01
 */
@RequestMapping(value = "/param")
public interface SpecParamFeign {
    @PostMapping(value = "/select-param-by-entity", consumes = "application/json")
    List<SpecParam> selectSpecParamApi(@RequestBody SpecParam entity);
}

