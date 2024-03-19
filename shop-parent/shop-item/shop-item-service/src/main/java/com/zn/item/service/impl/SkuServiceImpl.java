package com.zn.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zn.core.service.impl.ICrudServiceImpl;
import com.zn.item.dao.SkuDao;
import com.zn.item.service.SkuService;
import com.zn.shop.item.po.Sku;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/13---9:02
 */
@Service
public class SkuServiceImpl extends ICrudServiceImpl<Sku> implements SkuService {
    @Override
    public List<Sku> list(Sku entity) {
        QueryWrapper<Sku> queryWrapper = Wrappers.<Sku>query();
        if (entity.getSpuId() != null) {
            queryWrapper.eq("spu_id_", entity.getSpuId());
        }
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 减库存
     */
    @Override
    public int decrCount(Integer num, Long skuId) {
        return ((SkuDao) getBaseMapper()).decrCount(num, skuId);
    }
}
