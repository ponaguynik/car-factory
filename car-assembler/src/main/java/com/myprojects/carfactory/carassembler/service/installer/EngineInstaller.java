package com.myprojects.carfactory.carassembler.service.installer;

import com.myprojects.carfactory.carassembler.model.CarConstruct;
import com.myprojects.carfactory.model.CarPart;
import com.myprojects.carfactory.model.Engine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EngineInstaller implements CarPartInstaller {

    @Override
    public void installCarPart(CarConstruct construct, CarPart part) {
        Engine engine = castTo(part, Engine.class);
        construct.engine(engine);
        log.debug("Installed an Engine: {} on a car with assembly number: '{}'", part, construct.getAssemblyNumber());
    }

    @Override
    public boolean supports(CarPart part) {
        return Engine.class.isInstance(part);
    }
}
