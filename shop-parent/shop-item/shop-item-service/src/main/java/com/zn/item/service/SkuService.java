package com.zn.item.service;

import com.zn.core.service.ICrudService;
import com.zn.shop.item.po.Sku;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/13---9:01
 */
public interface SkuService extends ICrudService<Sku> {
    @Override
    List<Sku> list(Sku entity);

    int decrCount(Integer num, Long skuId);
}
