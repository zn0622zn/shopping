package com.zn.item.dao;

import com.zn.core.dao.ICrudDao;
import com.zn.shop.item.po.SpecParam;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/11---22:17
 */
@Repository
public interface SpecParamDao extends ICrudDao<SpecParam> {

    @Select("SELECT * FROM spec_param_ WHERE group_id_=#{groupId}")
    List<SpecParam> findByGroupId(Integer integer);
}
