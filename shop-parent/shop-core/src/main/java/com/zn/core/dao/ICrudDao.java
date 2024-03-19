package com.zn.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zn.core.po.BaseEntity;

import java.util.List;

public interface ICrudDao<T extends BaseEntity> extends BaseMapper<T> {

    /**
     * 动态sql语句查询
     */
    List<T> selectByPage(T entity);

}
