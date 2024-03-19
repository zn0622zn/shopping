package com.zn.item.service;

import com.zn.core.service.ICrudService;
import com.zn.shop.item.po.Spu;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/13---8:44
 */
@Service
public interface SpuService extends ICrudService<Spu> {

    void saveSpu(Spu spu);

    @Override
    default boolean removeById(Serializable id) {
        return ICrudService.super.removeById(id);
    }

    @Override
    default boolean removeByIds(Collection<? extends Serializable> idList) {
        return ICrudService.super.removeByIds(idList);
    }
}
