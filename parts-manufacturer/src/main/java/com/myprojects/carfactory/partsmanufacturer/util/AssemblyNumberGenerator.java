package com.myprojects.carfactory.partsmanufacturer.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AssemblyNumberGenerator {

    public String generateAssemblyNumber() {
        return UUID.randomUUID().toString();
    }
}
