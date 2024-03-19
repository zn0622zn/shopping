package com.zn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 张男
 * @date: 2024/2/14---10:34
 */
@Configuration
public class RedisConfig {
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);//初始化10个连接
        jedisPoolConfig.setMaxTotal(200);//最多200个连接
        jedisPoolConfig.setMaxWaitMillis(100_000);
        jedisPoolConfig.setMinIdle(1);
        return new JedisPool(jedisPoolConfig);
    }
}
