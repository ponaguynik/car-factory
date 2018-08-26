package com.myprojects.carfactory.model;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Engine extends CarPart {
    private String name;
    private double power;

    @Builder
    private Engine(String assemblyNumber, String name, double power) {
        this.assemblyNumber = assemblyNumber;
        this.name = name;
        this.power = power;
    }

    public Engine(Engine engine) {
        this.assemblyNumber = engine.getAssemblyNumber();
        this.name = engine.getName();
        this.power = engine.getPower();
    }
}
