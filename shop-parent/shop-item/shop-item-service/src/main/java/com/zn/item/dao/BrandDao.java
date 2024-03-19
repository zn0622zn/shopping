package com.zn.item.dao;

import com.zn.core.dao.ICrudDao;
import com.zn.shop.item.po.Brand;
import com.zn.shop.item.po.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 张男
 * @date 2023/11/28---17:57
 */
@Repository
public interface BrandDao extends ICrudDao<Brand> {

    /**
     * 根据商品id删除商品和分类之间的关联
     * @param id 商品id
     * @return 受影响行数
     */
    int deleteCategoryByBrand(List<Long> ids);

    /**
     * 根据分类id商品id进行关联
     * @param categoryId 分类id
     * @param brandId 商品id
     * @return 受影响行数
     */
    int insertCategoryAndBrand(@Param("categoryId") Long categoryId, @Param("brandId") Long brandId);

    /**
     *查询商品的分类
     * @param id 商品的id
     * @return 对应商品id的分类信息
     */
    List<Category> selectCategoryByBrand(Long id);

    /**
     * 根据分类id查询对应品牌
     */
    List<Brand> selectBrandByCategoryId(Long id);

}
