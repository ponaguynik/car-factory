package com.myprojects.carfactory.partsmanufacturer;

import com.myprojects.carfactory.model.CarPart;
import com.myprojects.carfactory.partsmanufacturer.client.CarAssemblerClient;
import com.myprojects.carfactory.partsmanufacturer.producer.CarPartProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
@Slf4j
public class CarPartsManufacturer {
    private final Set<CarPartProducer> producers;
    private final CarAssemblerClient carAssemblerClient;
    private final TaskScheduler manufactureTaskScheduler;

    @Autowired
    public CarPartsManufacturer(Set<CarPartProducer> producers, CarAssemblerClient carAssemblerClient,
                                TaskScheduler manufactureTaskScheduler) {
        this.producers = producers;
        this.carAssemblerClient = carAssemblerClient;
        this.manufactureTaskScheduler = manufactureTaskScheduler;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onStartup() {
        manufactureTaskScheduler.scheduleWithFixedDelay(this::manufacture, Duration.ofSeconds(1L));
        log.info("Started manufacturing parts task");
    }

    private void manufacture() {
        String assemblyNumber = UUID.randomUUID().toString();
        Set<Future<?>> futures = new HashSet<>();
        log.debug("Start manufacturing parts. Assembly number: {}", assemblyNumber);
        for (CarPartProducer producer : producers) {
            CompletableFuture<CarPart> future = produceAsync(assemblyNumber, producer);
            futures.add(future);
        }
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                log.warn("Interrupted car part producing");
            } catch (ExecutionException e) {
                log.error("Error when producing car part", e);
            }
        }
        log.debug("End manufacturing parts. Assembly number: {}", assemblyNumber);
    }

    private CarPart sendToAssembler(CarPart carPart, Throwable throwable) {
        if (carPart != null) {
            carAssemblerClient.sendCarPart(carPart);
            return carPart;
        } else if (throwable != null) {
            log.error("Exception when producing car part", throwable);
        } else {
            log.warn("No car part has been produced");
        }
        return null;
    }

    private CarPart handleException(Throwable throwable) {
        if (throwable != null) {
            log.error("Exception when sending car part to assembler", throwable);
        }
        return null;
    }

    @Async("partProducingExecutor")
    public CompletableFuture<CarPart> produceAsync(String assemblyNumber, CarPartProducer producer) {
        return CompletableFuture.supplyAsync(() -> producer.produce(assemblyNumber))
                .handle(this::sendToAssembler)
                .exceptionally(this::handleException);
    }
}
