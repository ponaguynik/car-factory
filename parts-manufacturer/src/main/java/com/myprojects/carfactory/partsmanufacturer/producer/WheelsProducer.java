package com.myprojects.carfactory.partsmanufacturer.producer;

import com.myprojects.carfactory.model.Wheel;
import com.myprojects.carfactory.model.Wheels;
import com.myprojects.carfactory.partsmanufacturer.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.myprojects.carfactory.partsmanufacturer.util.PropertyNames.WHEEL_PRODUCING_TIME_SEC;

@Slf4j
@Component
public class WheelsProducer implements CarPartProducer {
    private static final List<Wheels> WHEEL_SETS = Arrays.asList(
            Wheels.create()
                    .wheel(0, 0, Wheel.builder().model("First").size(1d).build())
                    .wheel(0, 1, Wheel.builder().model("First").size(1d).build())
                    .wheel(1, 0, Wheel.builder().model("First").size(1d).build())
                    .wheel(1, 1, Wheel.builder().model("First").size(1d).build()),
            Wheels.create()
                    .wheel(0, 0, Wheel.builder().model("Second").size(2d).build())
                    .wheel(0, 1, Wheel.builder().model("Second").size(2d).build())
                    .wheel(1, 0, Wheel.builder().model("Second").size(2d).build())
                    .wheel(1, 1, Wheel.builder().model("Second").size(2d).build()),
            Wheels.create()
                    .wheel(0, 0, Wheel.builder().model("Third").size(2d).build())
                    .wheel(0, 1, Wheel.builder().model("Third").size(2d).build())
                    .wheel(1, 0, Wheel.builder().model("Third").size(3d).build())
                    .wheel(1, 1, Wheel.builder().model("Third").size(3d).build())
    );

    @Value("${" + WHEEL_PRODUCING_TIME_SEC + "}")
    private Integer wheelProduceTimeSec;

    @Override
    public Wheels produce(String assemblyNumber) {
        for (int i = 0; i < 4; i++) {
            doProducing(wheelProduceTimeSec, TimeUnit.SECONDS);
        }
        Wheels wheels = CollectionUtils.getRandom(WHEEL_SETS);
        wheels.setAssemblyNumber(assemblyNumber);
        log.debug("Produce wheels: {}", wheels);
        return wheels;
    }


}
