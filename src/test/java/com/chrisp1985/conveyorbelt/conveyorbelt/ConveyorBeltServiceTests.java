package com.chrisp1985.conveyorbelt.conveyorbelt;

import com.chrisp1985.conveyorbelt.enums.Component;
import com.chrisp1985.conveyorbelt.objects.ConveyorBelt;
import com.chrisp1985.conveyorbelt.services.ConveyorBeltService;
import com.chrisp1985.conveyorbelt.configuration.ConveyorBeltServiceConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ConveyorBeltServiceTests {

    @Test
    void canProcessSuccessfully() {
        ConveyorBeltService conveyorBeltService = new ConveyorBeltService();
        ConveyorBeltServiceConfig processConfig = new ConveyorBeltServiceConfig();
        processConfig.setStepCount(100);
        processConfig.setWorkerPairCount(3);
        conveyorBeltService.process(processConfig, new ConveyorBelt(10));

        Assertions.assertTrue(conveyorBeltService.getBelt().getFinishedProducts() > 0);
    }

    @Test
    void cannotProcessStepCountZero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ConveyorBeltServiceConfig().setStepCount(0));
    }

    @Test
    void cannotProcessWorkerCountZero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ConveyorBeltServiceConfig().setWorkerPairCount(0));
    }

    @Test
    void canProcessSuccessfully_oneWorkerPair() {
        ConveyorBeltService conveyorBeltService = new ConveyorBeltService();
        ConveyorBeltServiceConfig processConfig = new ConveyorBeltServiceConfig();
        processConfig.setStepCount(100);
        processConfig.setWorkerPairCount(1);
        conveyorBeltService.process(processConfig, new ConveyorBelt(10));

        Assertions.assertTrue(conveyorBeltService.getBelt().getFinishedProducts() > 0);
        Assertions.assertTrue(conveyorBeltService.getBelt().getUnusedA() > 0);
        Assertions.assertTrue(conveyorBeltService.getBelt().getUnusedB() > 0);
    }

    @Test
    void canProcessSuccessfully_largeBeltSize() {
        ConveyorBeltService conveyorBeltService = new ConveyorBeltService();
        ConveyorBeltServiceConfig processConfig = new ConveyorBeltServiceConfig();
        processConfig.setStepCount(100);
        processConfig.setWorkerPairCount(3);
        conveyorBeltService.process(processConfig, new ConveyorBelt(1000));

        Assertions.assertEquals(0, conveyorBeltService.getBelt().getFinishedProducts());
        Assertions.assertEquals(0, conveyorBeltService.getBelt().getUnusedA());
        Assertions.assertEquals(0, conveyorBeltService.getBelt().getUnusedB());
    }

    @Test
    void canProcessComponentListSuccessfully() {
        ConveyorBeltService conveyorBeltService = new ConveyorBeltService();
        ConveyorBeltServiceConfig processConfig = new ConveyorBeltServiceConfig();
        processConfig.setStepCount(10);
        processConfig.setWorkerPairCount(3);

        List<Component> componentList = Arrays.asList(Component.A, Component.B, null, null, null, null, null, null);
        ConveyorBelt belt = new ConveyorBelt(4, componentList);

        conveyorBeltService.process(processConfig, belt);

        Assertions.assertEquals(1, conveyorBeltService.getBelt().getFinishedProducts());
    }

    @Test
    void canProcessComponentList_moreComponentsThanWorkers() {
        ConveyorBeltService conveyorBeltService = new ConveyorBeltService();
        ConveyorBeltServiceConfig processConfig = new ConveyorBeltServiceConfig();
        processConfig.setStepCount(7);
        processConfig.setWorkerPairCount(1);

        List<Component> componentList = Arrays.asList(Component.A, Component.A, Component.A, Component.A, Component.A, Component.A);
        ConveyorBelt belt = new ConveyorBelt(3, componentList);

        conveyorBeltService.process(processConfig, belt);

        Assertions.assertTrue(conveyorBeltService.getBelt().getUnusedA() > 0);
    }
}
