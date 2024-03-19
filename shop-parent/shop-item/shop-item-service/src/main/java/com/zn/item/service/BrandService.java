package com.zn.item.service;

import com.zn.core.service.ICrudService;
import com.zn.item.dao.BrandDao;
import com.zn.shop.item.po.Brand;
import com.zn.shop.item.po.Category;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface BrandService extends ICrudService<Brand> {

    /**
     * 根据商品id查询所属类别
     */
    List<Category> selectCategoryByBrand(Long id);

    List<Brand> selectBrandByIds(List<Long> ids);

    List<Brand> selectBrandByCid(Long cid);
}
