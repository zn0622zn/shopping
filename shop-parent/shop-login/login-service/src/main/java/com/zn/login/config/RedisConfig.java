package com.zn.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 张男
 * @date: 2024/1/24---16:17
 */
@Configuration
public class RedisConfig {
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);//最大维护连连接数量
        jedisPoolConfig.setMaxTotal(200);//最大连接数量200，tomcat最大连接200
        jedisPoolConfig.setMaxWaitMillis(10_000);//超时时间
        jedisPoolConfig.setMinIdle(1);//最小维护连接数量1
        return new JedisPool(jedisPoolConfig);
    }
}
