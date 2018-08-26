package com.myprojects.carfactory.partsmanufacturer.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.myprojects.carfactory.partsmanufacturer.producer.CarPartProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Set;

@Slf4j
@Configuration
public class AppConfig {

    @Bean
    public TaskExecutor partProducingExecutor(Set<CarPartProducer> producers) {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setThreadFactory(new ThreadFactoryBuilder()
                .setNameFormat("part-producer-%d")
                .setUncaughtExceptionHandler((t, e) -> log.error("Uncaught exception in thread: {}", t.getName(), e))
                .build());
        taskExecutor.setConcurrencyLimit(producers.size());
        return taskExecutor;
    }

    @Bean
    public TaskScheduler manufactureTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setThreadNamePrefix("ManufactureTaskScheduler-");
        taskScheduler.setErrorHandler(t -> log.error("Exception in ManufactureTaskScheduler", t));
        return taskScheduler;
    }
}
