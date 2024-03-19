package com.zn.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zn.core.service.impl.ICrudServiceImpl;
import com.zn.item.service.SpecParamService;
import com.zn.shop.item.po.SpecParam;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/12---15:02
 */
@Service
public class SpecParamServiceImpl extends ICrudServiceImpl<SpecParam> implements SpecParamService {
    @Override
    public List<SpecParam> list(SpecParam entity) {
        QueryWrapper<SpecParam> queryWrapper = Wrappers.<SpecParam>query();
        //根据分类id查询规格参数
        if (null != entity.getCid()) {
            queryWrapper.eq("cid_", entity.getCid());
        }
        if (null != entity.getSearching()) {
            queryWrapper.eq("searching_", entity.getSearching());
        }
        return getBaseMapper().selectList(queryWrapper);
    }
}
