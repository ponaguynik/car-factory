package com.myprojects.carfactory.partsmanufacturer.config;

import com.myprojects.carfactory.partsmanufacturer.producer.CarPartProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Set;
import java.util.concurrent.Semaphore;

import static com.myprojects.carfactory.partsmanufacturer.util.BeanNames.*;
import static com.myprojects.carfactory.partsmanufacturer.util.PropertyNames.SIMULTANEOUS_PRODUCING_CAR_NUMBER;

@Slf4j
@Configuration
public class AppConfig {
    @Value("${" + SIMULTANEOUS_PRODUCING_CAR_NUMBER + "}")
    private int simultaneousNumber;

    @Bean(PART_PRODUCING_EXECUTOR)
    public TaskExecutor partProducingExecutor(Set<CarPartProducer> producers) {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("part-");
        taskExecutor.setCorePoolSize(simultaneousNumber * producers.size());
        taskExecutor.setMaxPoolSize(taskExecutor.getCorePoolSize() + 3);
        return taskExecutor;
    }

    @Bean(BATCH_CAR_PARTS_PRODUCING_EXECUTOR)
    public TaskExecutor batchCarPartsProducingExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("batch-");
        taskExecutor.setCorePoolSize(simultaneousNumber);
        taskExecutor.setMaxPoolSize(taskExecutor.getCorePoolSize() + 3);
        return taskExecutor;
    }

    @Bean(MANUFACTURE_TASK_SCHEDULER)
    public TaskScheduler manufactureTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setThreadNamePrefix("ManufactureTaskScheduler-");
        taskScheduler.setErrorHandler(t -> log.error("Exception in ManufactureTaskScheduler", t));
        return taskScheduler;
    }

    @Bean(MANUFACTURE_CAR_PARTS_SEMAPHORE)
    public Semaphore manufactureCarPartsSemaphore() {
        log.info("Number of simultaneous producing of batches of car parts: {}", simultaneousNumber);
        return new Semaphore(simultaneousNumber);
    }
}
