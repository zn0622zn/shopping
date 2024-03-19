package com.zn.item.controller;

import com.zn.core.controller.BaseController;
import com.zn.item.service.CategoryService;
import com.zn.shop.item.po.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/11---12:15
 */
@RestController
@RequestMapping("category")
public class CategoryController extends BaseController<CategoryService, Category> {

    @GetMapping("/names")
    public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids") List<Long> ids) {
        List<String> names = service.selectNamesByIds(ids);
        if (null == names || names.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(names);
    }

}
