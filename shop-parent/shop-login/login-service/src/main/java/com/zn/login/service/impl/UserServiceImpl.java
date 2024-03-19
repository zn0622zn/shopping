package com.zn.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zn.core.po.ResponseBean;
import com.zn.core.service.impl.ICrudServiceImpl;
import com.zn.login.utils.MyJWT;
import com.zn.login.dao.RoleDao;
import com.zn.po.Role;
import com.zn.po.User;
import com.zn.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 张男
 * @date: 2024/1/23---20:11
 */
@Service
public class UserServiceImpl extends ICrudServiceImpl<User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleDao roleDao;

    @Override
    public ResponseBean selectUserByUsername(String username, String password) {
        ResponseBean responseBean = new ResponseBean();
        QueryWrapper<User> queryWrapper = Wrappers.query();
        queryWrapper.eq("user_name_", username);
        User user = getBaseMapper().selectOne(queryWrapper);
        if (user == null) {
            responseBean.setMessage("用户名错误");
            return responseBean;
        }
        boolean result = passwordEncoder.matches(password, user.getPassword());
        if (result) {//账号密码正确
            List<Role> roles = roleDao.selectRolesByUserId(user.getId());
            List<String> roleName = new ArrayList<>();
            //获取角色权限
            for (Role role : roles) {
                roleName.add(role.getName());
            }
            //构建jwt令牌
            String jwt = MyJWT.creatNewJwt(user, roleName);
            responseBean.setMessage("登录成功");
            responseBean.setToken(jwt);
            return responseBean;
        } else {
            responseBean.setMessage("密码错误");
            return responseBean;
        }
    }
}
