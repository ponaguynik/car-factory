package com.myprojects.carfactory.carassembler.service.impl;

import com.myprojects.carfactory.carassembler.client.CarServiceClient;
import com.myprojects.carfactory.carassembler.model.CarConstruct;
import com.myprojects.carfactory.carassembler.repository.CarConstructRepository;
import com.myprojects.carfactory.carassembler.service.CarAssemblyService;
import com.myprojects.carfactory.carassembler.service.installer.CarPartInstaller;
import com.myprojects.carfactory.model.CarPart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
public class CarAssemblyServiceImpl implements CarAssemblyService {
    private final CarConstructRepository repository;
    private final Set<CarPartInstaller> installers;
    private final CarServiceClient carServiceClient;

    @Autowired
    public CarAssemblyServiceImpl(CarConstructRepository repository, Set<CarPartInstaller> installers,
                                  CarServiceClient carServiceClient) {
        this.repository = repository;
        this.installers = installers;
        this.carServiceClient = carServiceClient;
    }

    // TODO: 30-Aug-18 remove synchronize. Make transactional
    @Override
    public synchronized void processCarPart(CarPart carPart) {
        CarConstruct carConstruct = resolveCarConstruct(carPart.getAssemblyNumber());
        for (CarPartInstaller installer : installers) {
            if (installer.supports(carPart)) {
                installer.installCarPart(carConstruct, carPart);
            }
        }
        if (carConstruct.isFullyAssembled()) {
            log.debug("Car with assembly number: '{}' is fully assembled. Sending to CarService",
                    carConstruct.getAssemblyNumber());
//            carServiceClient.sendConstructedCar(carConstruct);
            repository.deleteById(carConstruct.getAssemblyNumber());
        } else {
            repository.save(carConstruct);
        }
    }

    private CarConstruct resolveCarConstruct(String assemblyNumber) {
        return repository.findById(assemblyNumber)
                .orElseGet(() -> addNewCarConstruct(assemblyNumber));
    }

    private CarConstruct addNewCarConstruct(String assemblyNumber) {
        return repository.save(new CarConstruct(assemblyNumber));
    }
}
