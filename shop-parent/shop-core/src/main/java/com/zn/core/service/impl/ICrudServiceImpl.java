package com.zn.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zn.core.dao.ICrudDao;
import com.zn.core.po.BaseEntity;
import com.zn.core.service.ICrudService;

import java.util.List;

public class ICrudServiceImpl<T extends BaseEntity> extends ServiceImpl<ICrudDao<T>, T> implements ICrudService<T> {

    @Override
    public PageInfo<T> listPage(T entity, int pageNum, int pageSize) {
        //对查询语句添加分页处理 limit
        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            baseMapper.selectByPage(entity);
        });
    }


    @Override
    public List<T> list(T entity) {
        return getBaseMapper().selectList(Wrappers.emptyWrapper());
    }
}
