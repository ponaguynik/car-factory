package com.myprojects.carfactory.carassembler.service.installer;

import com.myprojects.carfactory.carassembler.model.CarConstruct;
import com.myprojects.carfactory.model.CarPart;
import com.myprojects.carfactory.model.Wheels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("!without-wheels")
public class WheelsInstaller implements CarPartInstaller {

    @Override
    public void installCarPart(CarConstruct construct, CarPart part) {
        Wheels wheels = castTo(part, Wheels.class);
        construct.wheels(wheels);
        log.debug("Installed Wheels: {} on a car with assembly number: '{}'", part, construct.getAssemblyNumber());
    }

    @Override
    public boolean supports(CarPart part) {
        return Wheels.class.isInstance(part);
    }
}
