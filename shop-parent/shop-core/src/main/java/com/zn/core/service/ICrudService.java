package com.zn.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zn.core.po.BaseEntity;

import java.util.List;

/**
 * 项目级扩展，分页查询，和全表查询...
 * @param <T>
 */
public interface ICrudService<T extends BaseEntity> extends IService<T> {

    public PageInfo<T> listPage(T entity, int pageNum, int pageSize);

    public List<T> list(T entity);

}
