package com.myprojects.carfactory.carassembler.client;

import com.myprojects.carfactory.carassembler.model.CarConstruct;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RibbonClient(CarServiceClient.SERVICE_ID)
@FeignClient(CarServiceClient.SERVICE_ID)
@Component
public interface CarServiceClient {
    String SERVICE_ID = "car-service";

    @RequestMapping(path = "/cars", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void sendConstructedCar(@RequestBody CarConstruct construct);
}
