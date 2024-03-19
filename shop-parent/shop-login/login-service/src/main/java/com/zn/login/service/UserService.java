package com.zn.login.service;

import com.zn.core.po.ResponseBean;
import com.zn.core.service.ICrudService;
import com.zn.po.User;

/**
 * @author 张男
 * @date: 2024/1/23---19:53
 */
public interface UserService extends ICrudService<User> {
    ResponseBean selectUserByUsername(String username, String password);
}
