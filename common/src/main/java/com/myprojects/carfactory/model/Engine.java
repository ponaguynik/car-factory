package com.myprojects.carfactory.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Engine extends CarPart {
    private String name;
    private double power;

    protected Engine() {
    }

    @Builder
    private Engine(String assemblyNumber, String name, double power) {
        super(assemblyNumber);
        this.name = name;
        this.power = power;
    }

    public Engine(Engine engine) {
        super(engine.getAssemblyNumber());
        this.name = engine.getName();
        this.power = engine.getPower();
    }
}
