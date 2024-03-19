package com.zn.gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @author 张男
 * @date: 2024/1/25---15:23
 */
public class JwtUtils {
    public static Claims parseJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey("znzn520")//密钥
                .parseClaimsJws(jwt)
                .getBody();
    }
}
