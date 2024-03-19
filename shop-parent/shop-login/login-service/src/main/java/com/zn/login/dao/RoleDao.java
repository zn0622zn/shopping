package com.zn.login.dao;

import com.zn.core.dao.ICrudDao;
import com.zn.po.Role;
import com.zn.po.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 张男
 * @date: 2024/1/25---11:09
 */
@Repository
public interface RoleDao extends ICrudDao<Role> {
    List<Role> selectRolesByUserId(Long id);
}
