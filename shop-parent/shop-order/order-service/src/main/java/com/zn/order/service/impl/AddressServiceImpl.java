package com.zn.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zn.core.service.impl.ICrudServiceImpl;
import com.zn.order.service.AddressService;
import com.zn.po.Address;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张男
 * @date: 2024/2/13---11:10
 */
@Service
public class AddressServiceImpl extends ICrudServiceImpl<Address> implements AddressService {
    @Override
    public List<Address> list(Address entity) {
        QueryWrapper<Address> queryWrapper = Wrappers.<Address>query();
        if (StringUtils.isNotEmpty(entity.getUsername())) {
            queryWrapper.eq("username_", entity.getUsername());
        }
        return getBaseMapper().selectList(queryWrapper);
    }
}
