package com.ibrahimRamadan.forexAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfig {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        // Making sure the scheduler is not running Asynchronously
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1); // Ensuring a single thread
        scheduler.setThreadNamePrefix("serial-scheduler-");
        return scheduler;
    }
}
