package com.myprojects.carfactory.partsmanufacturer.producer;

import com.myprojects.carfactory.model.Body;
import com.myprojects.carfactory.partsmanufacturer.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.myprojects.carfactory.partsmanufacturer.util.PropertyNames.BODY_PRODUCING_TIME_SEC;

@Slf4j
@Component
public class BodyProducer implements CarPartProducer {
    private static final List<Body> BODIES = Arrays.asList(
            Body.builder().type("first").height(3d).width(4d).length(5d).build(),
            Body.builder().type("second").height(2.5d).width(3d).length(4d).build(),
            Body.builder().type("third").height(3.5d).width(5d).length(5d).build(),
            Body.builder().type("fourth").height(4.5d).width(5d).length(7d).build()
    );

    @Value("${" + BODY_PRODUCING_TIME_SEC + "}")
    private Integer bodyProduceTimeSec;

    @Override
    public Body produce(String assemblyNumber) {
        doProducing(bodyProduceTimeSec, TimeUnit.SECONDS);
        Body body = new Body(CollectionUtils.getRandom(BODIES));
        body.setAssemblyNumber(assemblyNumber);
        log.debug("Produce body: {}", body);
        return body;
    }
}
