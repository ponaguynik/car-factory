package com.myprojects.carfactory.partsmanufacturer.producer;

import com.myprojects.carfactory.model.CarPart;
import com.myprojects.carfactory.model.Engine;
import com.myprojects.carfactory.partsmanufacturer.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.myprojects.carfactory.partsmanufacturer.util.BeanNames.PART_PRODUCING_EXECUTOR;
import static com.myprojects.carfactory.partsmanufacturer.util.PropertyNames.ENGINE_PRODUCING_TIME_SEC;

@Slf4j
@Component
public class EngineProducer implements CarPartProducer {
    private static final List<Engine> ENGINES = Arrays.asList(
            Engine.builder().name("First").power(100d).build(),
            Engine.builder().name("Second").power(200d).build(),
            Engine.builder().name("Third").power(300d).build(),
            Engine.builder().name("Fourth").power(400d).build(),
            Engine.builder().name("Fifth").power(500d).build()
    );

    @Value("${" + ENGINE_PRODUCING_TIME_SEC + "}")
    private Integer engineProduceTimeSec;

    @Override
    public Engine produce(String assemblyNumber) {
        doProducing(engineProduceTimeSec, TimeUnit.SECONDS);
        Engine engine = new Engine(CollectionUtils.getRandom(ENGINES));
        engine.setAssemblyNumber(assemblyNumber);
        log.debug("Produce engine: {}", engine);
        return engine;
    }

    @Async(PART_PRODUCING_EXECUTOR)
    @Override
    public CompletableFuture<CarPart> produceAsync(String assemblyNumber) {
        return CompletableFuture.completedFuture(produce(assemblyNumber));
    }
}
