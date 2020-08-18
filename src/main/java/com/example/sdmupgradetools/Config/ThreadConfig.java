/**
 * Project Name: sdm-upgradetools-web
 * File Name: ThreadConfig
 * Package Name: com.example.sdmupgradetools.Config
 * Date: 2020/5/13 12:20
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.example.sdmupgradetools.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 12:20 2020/5/13 
 */
@Configuration
//@EnableAsync
public class ThreadConfig {
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(30);
        // 设置最大线程数
        executor.setMaxPoolSize(60);
        // 设置队列容量
        executor.setQueueCapacity(60);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(600000);
        // 设置默认线程名称
        executor.setThreadNamePrefix("download-");
        // 设置拒绝策略
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
