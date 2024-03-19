package com.zn.item.service;

import com.zn.core.service.ICrudService;
import com.zn.shop.item.po.Category;

import java.io.Serializable;
import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/10---21:51
 */
public interface CategoryService extends ICrudService<Category> {
    /**
     * 根据传入的分类id集合，返回id对应的分类名字集合
     */
    List<String> selectNamesByIds(List<Long> ids);



}
