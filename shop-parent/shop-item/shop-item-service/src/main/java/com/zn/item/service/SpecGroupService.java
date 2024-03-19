package com.zn.item.service;

import com.zn.core.service.ICrudService;
import com.zn.shop.item.po.SpecGroup;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/12---14:53
 */
public interface SpecGroupService extends ICrudService<SpecGroup> {
    /**
     * 新增和不置为null的修改
     */
    void saveGroup(Long cid, List<SpecGroup> groups);

    /**
     * 置为null的修改(删除)
     */
    void doDelete(Long cid);
}
