package com.zn.item.dao;

import com.zn.core.dao.ICrudDao;
import com.zn.shop.item.po.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/12---19:41
 */
public interface SkuDao extends ICrudDao<Sku> {
    @Select("SELECT * FROM sku_ WHERE spu_id_=#{spuId}")
    List<Sku> findBySkuId(Integer spuId);

    @Update(value="update sku_ set stock_ = stock_ - #{num} where id_ =#{skuId} and stock_ >= #{num}")
    int decrCount(@Param("num") Integer num, @Param("skuId") Long skuId);

}
