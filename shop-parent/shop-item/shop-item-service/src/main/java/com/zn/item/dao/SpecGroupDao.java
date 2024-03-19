package com.zn.item.dao;

import com.zn.core.dao.ICrudDao;
import com.zn.shop.item.po.SpecGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/11---22:17
 */
@Repository
public interface SpecGroupDao extends ICrudDao<SpecGroup> {

     List<SpecGroup> selectList(SpecGroup specGroup);

}


