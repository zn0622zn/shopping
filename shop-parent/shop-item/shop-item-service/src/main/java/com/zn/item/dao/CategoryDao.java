package com.zn.item.dao;

import com.zn.core.dao.ICrudDao;
import com.zn.shop.item.po.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/10---21:43
 */
@Repository
public interface CategoryDao extends ICrudDao<Category> {
    /**
     * 根据categoryId删除中间映射表
     */
    int deleteCategoryAndBrandById(List<Long> ids);

    List<Category> selectCategorySomeCId(List<String> cIds);
}
