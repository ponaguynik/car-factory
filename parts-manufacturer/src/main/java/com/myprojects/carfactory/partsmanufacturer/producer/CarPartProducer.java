package com.myprojects.carfactory.partsmanufacturer.producer;

import com.myprojects.carfactory.model.CarPart;

import java.util.concurrent.TimeUnit;

public interface CarPartProducer {

    CarPart produce(String assemblyNumber);

    default void doProducing(long time, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException ignore) {
            // boost production :)
        }
    }
}
