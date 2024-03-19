package com.zn.dao;

import com.zn.core.dao.ICrudDao;
import com.zn.po.SeckillGoods;

/**
 * @author 张男
 * @date: 2023/12/25---16:14
 */
public interface SeckillGoodsDao extends ICrudDao<SeckillGoods> {
    void rollbackStock(Long id);
}
