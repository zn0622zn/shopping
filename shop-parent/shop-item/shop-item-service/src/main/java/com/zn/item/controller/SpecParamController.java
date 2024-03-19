package com.zn.item.controller;

import com.zn.core.controller.BaseController;
import com.zn.item.service.SpecParamService;
import com.zn.shop.item.po.SpecParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/12---15:41
 */
@RestController
@RequestMapping("/param")
public class SpecParamController extends BaseController<SpecParamService, SpecParam> {

    @PostMapping(value = "/select-param-by-entity")
    public List<SpecParam> selectSpecParamApi(@RequestBody SpecParam entity) {
        return service.list(entity);
    }

}
