package com.zn.item.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zn.core.service.impl.ICrudServiceImpl;
import com.zn.item.dao.SpecGroupDao;
import com.zn.item.dao.SpecParamDao;
import com.zn.item.service.SpecGroupService;
import com.zn.shop.item.po.SpecGroup;
import com.zn.shop.item.po.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/12---15:02
 */
@Service
public class SpecGroupServiceImpl extends ICrudServiceImpl<SpecGroup> implements SpecGroupService {
    @Autowired
    private SpecParamDao specParamDao;

    @Override
    public List<SpecGroup> list(SpecGroup entity) {
        return ((SpecGroupDao) getBaseMapper()).selectList(entity);
    }

    /**
     * 保存组的方式:先删除分类组和项，在按照数据，重新添加
     */
    @Override
    @Transactional
    public void saveGroup(Long cid, List<SpecGroup> groups) {
        //根据分类id删除该分类id的组
        getBaseMapper().delete(Wrappers.<SpecGroup>query().eq("cid_", cid));
        //根据分类id删除所有的项
        specParamDao.delete(Wrappers.<SpecParam>query().eq("cid_", cid));
        for (SpecGroup group : groups) {
            //将每一个组添加
            getBaseMapper().insert(group);
            for (SpecParam specParam : group.getParams()) {
                //防止给规格参数项的group_id自增
                specParam.setGroupId(group.getId());
                specParamDao.insert(specParam);
            }
        }
    }

    @Override
    @Transactional
    public void doDelete(Long cid) {
        baseMapper.delete(Wrappers.<SpecGroup>query().eq("cid_", cid));
        specParamDao.delete(Wrappers.<SpecParam>query().eq("cid_", cid));
        //或者为空也走saveGroup判断
    }

}
