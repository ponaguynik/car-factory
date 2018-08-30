package com.myprojects.carfactory.carassembler.service.installer;

import com.myprojects.carfactory.carassembler.model.CarConstruct;
import com.myprojects.carfactory.model.Body;
import com.myprojects.carfactory.model.CarPart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BodyInstaller implements CarPartInstaller {

    @Override
    public void installCarPart(CarConstruct construct, CarPart part) {
        Body body = castTo(part, Body.class);
        construct.body(body);
        log.debug("Installed a Body: {} on a car with assembly number: '{}'", part, construct.getAssemblyNumber());
    }

    @Override
    public boolean supports(CarPart part) {
        return Body.class.isInstance(part);
    }
}
