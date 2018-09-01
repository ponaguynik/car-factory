/*
package com.myprojects.carfactory.carassembler.actuator.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "assembling-status")
public class AssemblingStatusEndpoint {
    private final CarAssemblingTracker tracker;

    @Autowired
    public AssemblingStatusEndpoint(CarAssemblingTracker tracker) {
        this.tracker = tracker;
    }

    @ReadOperation
    public CarAssemblingTracker tracker() {
        return tracker;
    }

    @ReadOperation
    public CarAssemblingTracker.CarStatus statusByAssemblyNumber(@Selector String assemblyNumber) {
        return tracker.getStatusByAssemblyNumber(assemblyNumber);
    }
}
*/
