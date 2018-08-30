package com.myprojects.carfactory.carassembler.service.installer;

import com.google.common.base.Preconditions;
import com.myprojects.carfactory.carassembler.model.CarConstruct;
import com.myprojects.carfactory.model.CarPart;

public interface CarPartInstaller {

    void installCarPart(CarConstruct construct, CarPart part);

    boolean supports(CarPart part);

    default <T extends CarPart> T castTo(CarPart part, Class<T> clazz) {
        Preconditions.checkArgument(supports(part), "CarPart is not of " + clazz.getSimpleName() + " type");
        return clazz.cast(part);
    }
}
