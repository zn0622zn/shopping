//package com.zn.item.config;
//
//import com.zn.item.interceptor.LockInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @author 张男
// * @date: 2024/2/7---15:03
// */
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Bean
//    public LockInterceptor lockInterceptor() {
//        return new LockInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(this.lockInterceptor()).addPathPatterns("/**");
//    }
//}
