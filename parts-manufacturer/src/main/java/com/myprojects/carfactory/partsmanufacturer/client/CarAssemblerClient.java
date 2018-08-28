package com.myprojects.carfactory.partsmanufacturer.client;

import com.myprojects.carfactory.model.CarPart;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RibbonClient(CarAssemblerClient.SERVICE_ID)
@FeignClient(CarAssemblerClient.SERVICE_ID)
@Component
public interface CarAssemblerClient {
    String SERVICE_ID = "car-assembler";

    @RequestMapping(path = "/car-parts", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void sendCarPart(@RequestBody CarPart part);
}
