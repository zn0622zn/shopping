package com.zn.item.dao;

import com.zn.core.dao.ICrudDao;
import com.zn.shop.item.po.Spu;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/12---19:38
 */
public interface SpuDao extends ICrudDao<Spu> {
    List<Spu> selectSpuByBrandId(List<String> brandIds);
}
