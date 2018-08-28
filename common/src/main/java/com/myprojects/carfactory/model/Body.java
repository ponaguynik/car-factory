package com.myprojects.carfactory.model;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Body extends CarPart {
    private String type;
    private double width;
    private double height;
    private double length;

    protected Body() {
    }

    @Builder
    private Body(String assemblyNumber, String type, double width, double height, double length) {
        super(assemblyNumber);
        this.type = type;
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public Body(Body body) {
        super(body.getAssemblyNumber());
        this.type = body.getType();
        this.height = body.getHeight();
        this.width = body.getWidth();
        this.length = body.getLength();
    }

}
