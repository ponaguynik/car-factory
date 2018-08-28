package com.myprojects.carfactory.carassembler.controller;

import com.myprojects.carfactory.model.CarPart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CarPartController {

    @RequestMapping(path = "/car-parts", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addCarPart(@RequestBody CarPart part) {
        log.info("Got new car part: {}", part);
    }
}
