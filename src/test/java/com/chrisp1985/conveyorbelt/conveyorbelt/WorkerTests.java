package com.chrisp1985.conveyorbelt.conveyorbelt;

import com.chrisp1985.conveyorbelt.enums.Component;
import com.chrisp1985.conveyorbelt.objects.Worker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorkerTests {

    @Test
    void workerCanMakeProductIfHandsFull() {
        Worker worker = new Worker();

        worker.takeComponent(Component.A);
        Assertions.assertFalse(worker.canMakeProduct());

        worker.takeComponent(Component.B);
        Assertions.assertTrue(worker.canMakeProduct());
    }

    @Test
    void workerSetToAssembleOnCreateProduct() {
        Worker worker = new Worker();

        worker.takeComponent(Component.A);
        worker.takeComponent(Component.B);
        worker.createProduct();

        Assertions.assertTrue(worker.isAssembling());
    }

    @Test
    void workerIsAssemblingWhenTimeGreaterThanZero() {
        Worker worker = new Worker();

        worker.setTimeLeftToBuild(2);
        Assertions.assertTrue(worker.isAssembling());

        worker.setTimeLeftToBuild(0);
        Assertions.assertFalse(worker.isAssembling());
    }

    @Test
    void workerHasFinishedWhenTimeIsOne() {
        Worker worker = new Worker();

        worker.setTimeLeftToBuild(1);
        Assertions.assertTrue(worker.hasFinishedProduct());

        worker.setTimeLeftToBuild(2);
        Assertions.assertFalse(worker.hasFinishedProduct());
    }

    @Test
    void workerCanSuccessfullyEmptyHands() {
        Worker worker = new Worker();

        worker.setEmptyHands();
        Assertions.assertNull(worker.getLeftHand());
        Assertions.assertNull(worker.getRightHand());

        worker.takeComponent(Component.A);
        worker.takeComponent(Component.B);

        Assertions.assertNotNull(worker.getLeftHand());
        Assertions.assertNotNull(worker.getRightHand());
    }
}
