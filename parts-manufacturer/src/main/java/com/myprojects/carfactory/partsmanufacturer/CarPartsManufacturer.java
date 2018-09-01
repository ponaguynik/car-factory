package com.myprojects.carfactory.partsmanufacturer;

import com.myprojects.carfactory.model.CarPart;
import com.myprojects.carfactory.partsmanufacturer.client.CarAssemblerClient;
import com.myprojects.carfactory.partsmanufacturer.service.CarPartBatchProductionService;
import com.myprojects.carfactory.partsmanufacturer.util.AssemblyNumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.Semaphore;

import static com.myprojects.carfactory.partsmanufacturer.util.BeanNames.MANUFACTURE_CAR_PARTS_SEMAPHORE;

@Slf4j
@Component
public class CarPartsManufacturer {
    private final CarPartBatchProductionService batchProductionService;
    private final CarAssemblerClient carAssemblerClient;
    private final AssemblyNumberGenerator assemblyNumberGenerator;

    private final TaskScheduler manufactureTaskScheduler;
    private final Semaphore semaphore;

    @Autowired
    public CarPartsManufacturer(CarAssemblerClient carAssemblerClient,
                                CarPartBatchProductionService batchProductionService,
                                AssemblyNumberGenerator assemblyNumberGenerator,
                                TaskScheduler manufactureTaskScheduler,
                                @Qualifier(MANUFACTURE_CAR_PARTS_SEMAPHORE) Semaphore semaphore) {
        this.carAssemblerClient = carAssemblerClient;
        this.batchProductionService = batchProductionService;
        this.assemblyNumberGenerator = assemblyNumberGenerator;
        this.manufactureTaskScheduler = manufactureTaskScheduler;
        this.semaphore = semaphore;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onStartup() {
        manufactureTaskScheduler.scheduleWithFixedDelay(this::manufacture, Duration.ofSeconds(1L));
        log.info("Started manufacturing parts task");
    }

    private void manufacture() {
        try {
            semaphore.acquire();
            String assemblyNumber = assemblyNumberGenerator.generateAssemblyNumber();
            batchProductionService.produceBatchOfPartsAsync(assemblyNumber, this::sendToAssembler);
        } catch (InterruptedException e) {
            log.warn("Interrupted producing of batch of car parts");
        }
    }

    private void sendToAssembler(CarPart part) {
        try {
            carAssemblerClient.sendCarPart(part);
            log.debug("Sent car part: {} to assembler service", part);
        } catch (Exception e) {
            log.error("Exception when sending car part to assembler", e);
        }
    }
}
