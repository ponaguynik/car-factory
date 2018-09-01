package com.myprojects.carfactory.partsmanufacturer.producer;

import com.myprojects.carfactory.model.CarPart;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public interface CarPartProducer {

    CarPart produce(String assemblyNumber);

    CompletableFuture<CarPart> produceAsync(String assemblyNumber);

    default void doProducing(long time, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException ignore) {
            // boost production :)
        }
    }
}
