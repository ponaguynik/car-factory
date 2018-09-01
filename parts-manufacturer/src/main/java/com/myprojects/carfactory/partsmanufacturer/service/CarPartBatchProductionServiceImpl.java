package com.myprojects.carfactory.partsmanufacturer.service;

import com.myprojects.carfactory.model.CarPart;
import com.myprojects.carfactory.partsmanufacturer.producer.CarPartProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Consumer;

import static com.myprojects.carfactory.partsmanufacturer.util.BeanNames.BATCH_CAR_PARTS_PRODUCING_EXECUTOR;

@Slf4j
@Service
public class CarPartBatchProductionServiceImpl implements CarPartBatchProductionService {
    private final Set<CarPartProducer> producers;
    private final Semaphore semaphore;

    @Autowired
    public CarPartBatchProductionServiceImpl(Set<CarPartProducer> producers, Semaphore semaphore) {
        this.producers = producers;
        this.semaphore = semaphore;
    }

    @Async(BATCH_CAR_PARTS_PRODUCING_EXECUTOR)
    @Override
    public Future<Set<CarPart>> produceBatchOfPartsAsync(String assemblyNumber) {
        return produceBatchOfPartsAsync(assemblyNumber, null);
    }

    @Async(BATCH_CAR_PARTS_PRODUCING_EXECUTOR)
    @Override
    public Future<Set<CarPart>> produceBatchOfPartsAsync(String assemblyNumber, @Nullable Consumer<CarPart> afterProduce) {
        log.debug("Start manufacturing parts. Assembly number: {}", assemblyNumber);
        Set<Future<CarPart>> futures = new HashSet<>();
        for (CarPartProducer producer : producers) {
            CompletableFuture<CarPart> completableFuture = producer.produceAsync(assemblyNumber)
                    .thenApply(part -> applyAfterProduce(part, afterProduce));
            futures.add(completableFuture);
        }
        Set<CarPart> result = new HashSet<>();
        for (Future<CarPart> future : futures) {
            try {
                result.add(future.get());
            } catch (InterruptedException | CancellationException e) {
                log.warn("Interrupted producing of a car part");
                break;
            } catch (ExecutionException e) {
                log.error("Error when producing car part", e);
            }
        }
        log.debug("End manufacturing parts. Assembly number: {}", assemblyNumber);
        semaphore.release();
        return new AsyncResult<>(result);
    }

    private CarPart applyAfterProduce(CarPart part, @Nullable Consumer<CarPart> afterProduce) {
        if (afterProduce != null) {
            afterProduce.accept(part);
        }
        return part;
    }
}
