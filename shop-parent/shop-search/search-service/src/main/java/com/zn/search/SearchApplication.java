package com.zn.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 张男
 * @date: 2023/12/15---12:19
 */
@SpringBootApplication//springboot 服务
@EnableDiscoveryClient//注册到注册中心
@EnableFeignClients//通过feign接口调用其它微服务
@EnableCircuitBreaker//开启熔断
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}
