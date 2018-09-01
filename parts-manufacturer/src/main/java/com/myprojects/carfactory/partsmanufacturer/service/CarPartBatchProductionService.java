package com.myprojects.carfactory.partsmanufacturer.service;

import com.myprojects.carfactory.model.CarPart;

import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public interface CarPartBatchProductionService {

    Future<Set<CarPart>> produceBatchOfPartsAsync(String assemblyNumber);

    Future<Set<CarPart>> produceBatchOfPartsAsync(String assemblyNumber, Consumer<CarPart> afterProduce);
}
