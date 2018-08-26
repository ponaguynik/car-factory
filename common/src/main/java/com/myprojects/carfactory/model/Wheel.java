package com.myprojects.carfactory.model;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Wheel extends CarPart {
    private String model;
    private double size;

    @Builder
    public Wheel(String assemblyNumber, String model, double size) {
        this.assemblyNumber = assemblyNumber;
        this.model = model;
        this.size = size;
    }

    public Wheel(Wheel wheel) {
        this.assemblyNumber = wheel.getAssemblyNumber();
        this.model = wheel.getModel();
        this.size = wheel.getSize();
    }
}
