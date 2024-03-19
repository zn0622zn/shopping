package com.zn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 张男
 * @date: 2024/3/15---10:40
 */
@Configuration
public class ThreadPool {

    @Bean("myPool")
    public ThreadPoolExecutor getThreadPool() {
        return new ThreadPoolExecutor(
                200,
                400,
                30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(200)
        );
    }
}
