package com.zn.service.impl;

import com.alibaba.fastjson2.JSON;
import com.zn.core.service.impl.ICrudServiceImpl;
import com.zn.dao.SeckillGoodsDao;
import com.zn.po.SeckillGoods;
import com.zn.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/25---16:37
 */
@Service
public class SeckillGoodsServiceImpl extends ICrudServiceImpl<SeckillGoods> implements SeckillGoodsService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    @Override
    public List<SeckillGoods> list(String key) {
        Jedis resource = jedisPool.getResource();
        List<String> hvals = resource.hvals("SeckillGoods-" + key);
        ArrayList<SeckillGoods> seckillGoods = new ArrayList<>();
        for (String s : hvals) {
            seckillGoods.add(JSON.parseObject(s, SeckillGoods.class));
        }
        resource.close();
        return seckillGoods;
    }

    @Override
    public SeckillGoods one(String time, Long id) {
        Jedis resource = jedisPool.getResource();
        String seckillGoodStr = resource.hget("SeckillGoods-" + time, String.valueOf(id));
        resource.close();
        return JSON.parseObject(seckillGoodStr, SeckillGoods.class);
    }

    @Override
    public void deleteSeckillGoodsInRedis(String time, String id) {
        Jedis resource = jedisPool.getResource();
        resource.hdel("SeckillGoods-" + time, id);
        resource.close();
    }

    @Override
    public void rollbackCount(Long id) {
        seckillGoodsDao.rollbackStock(id);
    }
}
