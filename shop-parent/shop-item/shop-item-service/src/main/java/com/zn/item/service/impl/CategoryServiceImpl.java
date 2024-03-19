package com.zn.item.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zn.core.po.BaseTreeEntity;
import com.zn.core.service.impl.ICrudServiceImpl;
import com.zn.item.dao.CategoryDao;
import com.zn.item.service.CategoryService;
import com.zn.shop.item.po.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * @author 张男
 * @date: 2023/12/10---21:52
 */
@Service
public class CategoryServiceImpl extends ICrudServiceImpl<Category> implements CategoryService {

    @Autowired
    private JedisPool jedisPool;

    public static final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public List<Category> list(Category category) {
        try {
            lock.readLock().lock();//读锁
            QueryWrapper<Category> queryWrapper = Wrappers.query();
            //查全表(用于门户的频繁访问以及后台的查询全表)
            if (category.getTitle() == null || category.getTitle().equals("")) {
                Jedis resource = jedisPool.getResource();
                String s = resource.get("category-all");
                if (s != null) {
                    resource.expire("category-all", 600);
                    resource.close();
                    return JSON.parseArray(s, Category.class);
                } else {
                    List<Category> categories = getBaseMapper().selectList(queryWrapper);
                    //1h过期
                    resource.setex("category-all", 600, JSON.toJSONString(categories));
                    resource.close();
                    return categories;
                }
            } else {
                //根据分类名的模糊查询(用于后台的查询)
                queryWrapper.like("title_", category.getTitle());
                return getBaseMapper().selectList(queryWrapper);
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean saveOrUpdate(Category entity) {
        Jedis resource = null;
        try {
            lock.writeLock().lock();
            resource = jedisPool.getResource();
            resource.del("category-all");
            return super.saveOrUpdate(entity);
        } finally {
            if (resource != null) {
                resource.close();
            }
            lock.writeLock().unlock();
        }
    }


    @Override
    public List<String> selectNamesByIds(List<Long> ids) {
        QueryWrapper<Category> queryWrapper = Wrappers.<Category>query().in("id_", ids);
        return getBaseMapper().selectList(queryWrapper).stream()
                .map(BaseTreeEntity::getTitle).collect(Collectors.toList());
    }


    /**
     * 删除分类
     */
    @Override
    @Transactional
    public boolean removeById(Serializable id) {
        try (Jedis resource = jedisPool.getResource()) {
            lock.writeLock().lock();
            List<Long> ids = new ArrayList<>();
            Category category = baseMapper.selectById(id);
            //不是父节点
            if (!category.getIsParent()) {
                //删除分类信息
                int resultA = baseMapper.deleteById(id);
                ids.add((Long) id);
                //删除分类品牌映射信息
                int resultB = ((CategoryDao) getBaseMapper()).deleteCategoryAndBrandById(ids);
                //删除缓存分类信息
                resource.del("category-all");
                return (resultA >= 0 && resultB >= 0);
            } else {//是父节点
                List<Long> resultIds = this.deleteParentNode((Long) id, ids);
                int resultA = ((CategoryDao) getBaseMapper()).deleteCategoryAndBrandById(resultIds);
                int resultB = baseMapper.deleteBatchIds(resultIds);
                if (resultA >= 0 && resultB >= 0) {
                    resource.del("category-all");
                    return true;
                } else {
                    resource.del("category-all");
                    return false;
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 删除父节点的功能，碍于前端的问题，此处无法实现
     */
    private List<Long> deleteParentNode(Long id, List<Long> ids) {
        QueryWrapper<Category> queryWrapper = Wrappers.query();
        queryWrapper.eq("parent_id_", id);
        List<Category> categories = baseMapper.selectList(queryWrapper);
        //删除当前节点(即父节点)
        baseMapper.deleteById(id);
        //对子节点遍历
        for (Category category : categories) {
            //如果不是父节点
            if (!category.getIsParent()) {
                ids.add(category.getId());
            } else {//如果是父节点
                this.deleteParentNode(category.getId(), ids);
            }
        }
        return ids;
    }
}
