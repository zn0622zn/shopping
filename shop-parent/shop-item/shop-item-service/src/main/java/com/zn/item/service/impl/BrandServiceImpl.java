package com.zn.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zn.core.service.impl.ICrudServiceImpl;
import com.zn.item.dao.BrandDao;
import com.zn.item.service.BrandService;
import com.zn.shop.item.po.Brand;
import com.zn.shop.item.po.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BrandServiceImpl extends ICrudServiceImpl<Brand> implements BrandService {

    @Override
    @Transactional//事务
    public boolean saveOrUpdate(Brand entity) {
        //拿到brand的id值，添加则id值为null
        List<Long> id = new ArrayList<>();
        id.add(entity.getId());
        //将品牌信息保存下来(无categoryIds字段)
        boolean result = super.saveOrUpdate(entity);
        //删除原来的品牌分类对应关系
        ((BrandDao) getBaseMapper()).deleteCategoryByBrand(id);
        //拿到商品所属分类id
        Long[] roleIds = entity.getCategoryIds();
        if (null != roleIds) {
            //将商品id和所属分类id重新进行关联
            for (Long roleId : roleIds) {
                ((BrandDao) getBaseMapper())
                        .insertCategoryAndBrand(roleId, entity.getId());
            }
        }
        return result;
    }

    /**
     * 根据商品id返回分类信息
     */
    @Override
    public List<Category> selectCategoryByBrand(Long id) {
        BrandDao brandDao = (BrandDao) getBaseMapper();
        return brandDao.selectCategoryByBrand(id);
    }

    @Override
    public List<Brand> selectBrandByIds(List<Long> ids) {
        QueryWrapper<Brand> queryWrapper = Wrappers.<Brand>query().in("id_", ids);
        return getBaseMapper().selectList(queryWrapper);
    }

    @Override
    public List<Brand> selectBrandByCid(Long cId) {
        return ((BrandDao) getBaseMapper()).selectBrandByCategoryId(cId);
    }

    @Override
    @Transactional
    public boolean removeById(Serializable id) {
        //删除brand
        boolean result = super.removeById(id);
        //删除商品和分类的关联
        List<Long> ids = new ArrayList<>();
        ids.add((Long) id);
        int s = ((BrandDao) getBaseMapper()).deleteCategoryByBrand(ids);
        if (result && s > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        boolean result = super.removeByIds(idList);
        int s = ((BrandDao) getBaseMapper()).deleteCategoryByBrand((List<Long>) idList);
        if (result && s > 0) {
            return true;
        }
        return false;
    }
}
