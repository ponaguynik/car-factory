package com.myprojects.carfactory.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Wheel extends CarPart {
    private String model;
    private double size;

    public Wheel() {
    }

    @Builder
    public Wheel(String assemblyNumber, String model, double size) {
        super(assemblyNumber);
        this.model = model;
        this.size = size;
    }

    public Wheel(Wheel wheel) {
        super(wheel.getAssemblyNumber());
        this.model = wheel.getModel();
        this.size = wheel.getSize();
    }
}
