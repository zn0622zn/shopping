package com.zn.login.config;

import com.github.pagehelper.PageInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 张男
 * @date: 2024/1/24---14:18
 */
@Configuration
@MapperScan("com.zn.login.dao")
public class MybatisConfig {
    /**
     * 分页查询插件
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        return new PageInterceptor();
    }
}
