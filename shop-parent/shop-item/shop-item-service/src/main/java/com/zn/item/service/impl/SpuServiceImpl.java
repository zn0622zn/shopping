package com.zn.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zn.core.service.impl.ICrudServiceImpl;
import com.zn.item.service.SkuService;
import com.zn.item.service.SpuDetailService;
import com.zn.item.service.SpuService;
import com.zn.shop.item.po.Sku;
import com.zn.shop.item.po.Spu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/13---8:45
 */
@Service
public class SpuServiceImpl extends ICrudServiceImpl<Spu> implements SpuService {

    @Autowired
    private SpuDetailService spuDetailService;

    @Autowired
    private SkuService skuService;

    /**
     * 保存Spu(有id修改，无id添加)
     * 保存SpuDetail
     * 删除Sku对应的所有params
     * 添加params
     */
    @Override
    public void saveSpu(Spu spu) {
        //保存Spu
        super.saveOrUpdate(spu);
        //保存SpuDetail
        if (spu.getSpuDetail().getId() == null) {//如果是添加操作
            spu.getSpuDetail().setId(spu.getId());//给SpuDetail赋值一样的id
            spuDetailService.save(spu.getSpuDetail());
        } else {//如果是修改操作
            spuDetailService.updateById(spu.getSpuDetail());
        }
        //删除spu的所有sku
        skuService.remove(Wrappers.<Sku>query().eq("spu_id_", spu.getId()));
        //添加新的sku
        for (Sku sku : spu.getSkus()) {
            sku.setSpuId(spu.getId());
            skuService.save(sku);
        }
    }

    @Override
    @Transactional
    public boolean removeById(Serializable id) {
        //根据spuId删除spu
        int resultA = baseMapper.deleteById(id);
        boolean resultB = skuService.remove(Wrappers.<Sku>query().eq("spu_id_", id));
        return resultB && resultA >= 0;
    }

    @Override
    @Transactional
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        for (Serializable serializable : idList) {
            this.removeById((Serializable) idList);
        }
        return true;
    }
}
