package com.myprojects.carfactory.carassembler.model;

import com.myprojects.carfactory.model.Body;
import com.myprojects.carfactory.model.Engine;
import com.myprojects.carfactory.model.Wheels;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Setter(AccessLevel.PROTECTED)
@RedisHash("car-constructs")
public class CarConstruct {
    @Id
    private String assemblyNumber;
    private Engine engine;
    private Body body;
    private Wheels wheels;

    public CarConstruct(String assemblyNumber) {
        this.assemblyNumber = assemblyNumber;
    }

    protected CarConstruct() {
    }

    public void engine(Engine engine) {
        this.engine = engine;
    }

    public void body(Body body) {
        this.body = body;
    }

    public void wheels(Wheels wheels) {
        this.wheels = wheels;
    }

    public boolean isFullyAssembled() {
        return assemblyNumber != null && engine != null && body != null && wheels != null;
    }
}