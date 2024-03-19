package com.zn.gateway.filter;

import com.zn.gateway.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 登录拦截
 *
 * @author 张男
 * @date: 2024/1/23---10:32
 */
@Component
public class TokenFilter implements GlobalFilter, Ordered {

    /**
     * 放行的接口
     */
    private static final List<String> GO_URL = Arrays.asList("/gateway/login/doLogin");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.如果是登录请求或其它无需权限请求放行
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();
        System.err.println(path);
        if (GO_URL.contains(path)) {
            return chain.filter(exchange);
        }
        //拿到jwt token
        String token = request.getHeaders().getFirst("Authorization");
        if (StringUtils.isEmpty(token)) {//如果无令牌，证明还未登录
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        } else {//如果有令牌，校验令牌
            try {
                String jwt = token.split(" ")[1];
                Claims claims = JwtUtils.parseJWT(jwt);
                request.mutate().header("roles", claims.get("role").toString())
                        .header("lock", claims.get("lock").toString())
                        .header("uId", claims.getId())
                        .header("username", claims.get("username").toString());
            } catch (Exception e) {
                //解析出现异常
                System.err.println("令牌解析出现异常");
                response.setStatusCode(HttpStatus.FORBIDDEN);
                return response.setComplete();
            }
            return chain.filter(exchange.mutate().request(request).build());
        }
    }

    /**
     * 优先级越小越先执行
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
