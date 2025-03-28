package com.chrisp1985.conveyorbelt;

import com.chrisp1985.conveyorbelt.objects.ConveyorBelt;
import com.chrisp1985.conveyorbelt.services.ConveyorBeltService;
import com.chrisp1985.conveyorbelt.services.ConveyorBeltServiceConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@Slf4j
@EnableRetry
@RequiredArgsConstructor
public class ConveyorBeltApplication implements CommandLineRunner {

    @Value("${conveyor.belt.stepCount}")
    Integer stepCount;

    @Value("${conveyor.belt.workerPairCount}")
    Integer workerPairCount;

    @Value("${conveyor.belt.length}")
    Integer beltLength;

    @Autowired
    private final ConveyorBeltService conveyorBeltService;

    public static void main(String[] args) {
        SpringApplication.run(ConveyorBeltApplication.class, args);
    }

    @Override
    public void run(String... args) {

        try {
            stepCount = Integer.parseInt(args[0]);
        }
        catch (Exception e) {
            log.error("No stepCount supplied. Using default value.");
        }

        try {
            workerPairCount = Integer.parseInt(args[1]);
        }
        catch (Exception e) {
            log.error("No workerPairCount supplied. Using default value.");
        }

        try {
            beltLength = Integer.parseInt(args[2]);
        }
        catch (Exception e) {
            log.error("No beltLength supplied. Using default value.");
        }

        ConveyorBeltService conveyorBeltService = new ConveyorBeltService();
        ConveyorBeltServiceConfig processConfig = new ConveyorBeltServiceConfig();
        processConfig.setStepCount(stepCount);
        processConfig.setWorkerPairCount(workerPairCount);
        conveyorBeltService.process(processConfig, new ConveyorBelt(beltLength));
    }
}