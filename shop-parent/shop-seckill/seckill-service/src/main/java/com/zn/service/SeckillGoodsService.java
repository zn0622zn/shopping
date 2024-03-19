package com.zn.service;

import com.zn.core.service.ICrudService;
import com.zn.po.SeckillGoods;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/25---16:37
 */
public interface SeckillGoodsService extends ICrudService<SeckillGoods> {

    /**
     * 获取指定时间的商品秒杀列表
     * @param key
     * @return
     */
    List<SeckillGoods> list(String key);

    /**
     * 根据id查询秒杀商品的信息
     * @return
     */
    SeckillGoods one(String time,Long id);

    void deleteSeckillGoodsInRedis(String time, String id);

    void rollbackCount(Long id);

}
