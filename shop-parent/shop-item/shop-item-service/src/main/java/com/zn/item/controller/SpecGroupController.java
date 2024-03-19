package com.zn.item.controller;

import com.zn.core.controller.BaseController;
import com.zn.core.po.ResponseBean;
import com.zn.item.service.SpecGroupService;
import com.zn.shop.item.po.SpecGroup;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/12---15:20
 */
@RestController
@RequestMapping("/group")
public class SpecGroupController extends BaseController<SpecGroupService, SpecGroup> {



    @PostMapping("/save-group")
    public ResponseBean saveGroup(@RequestBody List<SpecGroup> specGroup) {
        ResponseBean rm = new ResponseBean();
        try {
            if (specGroup != null && specGroup.size() > 0) {
                //当前分类id，和所有分组
                service.saveGroup(specGroup.get(0).getCid(), specGroup);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rm.setSuccess(false);
            rm.setMessage("保存失败");
        }
        return rm;
    }
}
