package com.zn.item.config;

import com.github.pagehelper.PageInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mp分页插件配置类
 */
@Configuration
@MapperScan("com.zn.item.dao")
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        return new PageInterceptor();
    }
}