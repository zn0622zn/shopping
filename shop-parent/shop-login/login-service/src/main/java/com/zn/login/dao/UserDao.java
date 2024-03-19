package com.zn.login.dao;

import com.zn.core.dao.ICrudDao;
import com.zn.po.User;
import org.springframework.stereotype.Repository;


/**
 * @author 张男
 * @date: 2024/1/24---14:16
 */
@Repository
public interface UserDao extends ICrudDao<User> {
    void addPoint(Long point,String username);
}
