package com.zn.login.utils;

import com.zn.po.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author 张男
 * @date: 2024/1/25---14:36
 */
public class MyJWT {
    /**
     * 密钥
     */
    private static final String MI_YAO = "znzn520";

    /**
     * 创建jwt令牌
     * @return jwt
     */
    public static String creatNewJwt(User user, List<String> roleName) {
        long now = System.currentTimeMillis();
        long exTime = now + 43200_000;//过期时间12h// ;
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", user.getUserName());//用户名
        map.put("lock", user.getLock());//是否是锁定状体
        map.put("role", roleName);//权限
        JwtBuilder jwtBuilder = Jwts.builder().setId(user.getId().toString())//jwt编号
                .setSubject("jwt令牌")//主题
                .setIssuedAt(new Date())//jwt签发时间
                .setExpiration(new Date(exTime))//过期时间10min
                .addClaims(map)//自定义载荷
                .signWith(SignatureAlgorithm.HS256, MI_YAO);//HS256加密，密钥;
        return jwtBuilder.compact();
    }
}
