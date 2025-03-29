package com.chrisp1985.conveyorbelt.services;

import com.chrisp1985.conveyorbelt.configuration.ConveyorBeltServiceConfig;
import com.chrisp1985.conveyorbelt.enums.Component;
import com.chrisp1985.conveyorbelt.enums.Item;
import com.chrisp1985.conveyorbelt.enums.Product;
import com.chrisp1985.conveyorbelt.objects.ConveyorBelt;
import com.chrisp1985.conveyorbelt.objects.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConveyorBeltService {

    private ConveyorBelt belt;

    public void process(ConveyorBeltServiceConfig config, ConveyorBelt belt) {
        int stepCount = config.getStepCount();
        int workerPairCount = config.getWorkerPairCount();
        this.setBelt(belt);

        log.info("Running with StepCount {}, WorkerPairCount {} and Belt Length {}",
                stepCount,
                workerPairCount,
                belt.getLength());

        Worker[][] workerPairs = initialiseWorkers(workerPairCount);

        belt.advance();

        for (int step = 0; step < stepCount; step++) {
            processWorkerActions(workerPairs, belt);
            belt.advance();
        }

        printResults(belt);
    }

    public Worker[][] initialiseWorkers(int workerPairCount) {
        Worker[][] workerPairs = new Worker[workerPairCount][2];
        for (int i = 0; i < workerPairCount; i++) {
            workerPairs[i][0] = new Worker();
            workerPairs[i][1] = new Worker();
        }
        return workerPairs;
    }

    public Worker[][] initialiseWorkers(int workerSlotCount, int sizeOfWorkers) {
        Worker[][] workerPairs = new Worker[workerSlotCount][sizeOfWorkers];
        for (int i = 0; i < workerSlotCount; i++) {
            for (int j = 0; j < workerSlotCount; j++) {
                workerPairs[i][j] = new Worker();
            }
        }
        return workerPairs;
    }

    private void processWorkerActions(Worker[][] workerPairs, ConveyorBelt belt) {
        for (int i = 0; i < workerPairs.length; i++) {
            Worker leftWorker = workerPairs[i][0];
            Worker rightWorker = workerPairs[i][1];

            processWorker(leftWorker, belt, i);
            processWorker(rightWorker, belt, i);

            if (belt.getSlot(i).isEmpty()) {
                placeFinishedProduct(leftWorker, belt, i);
                placeFinishedProduct(rightWorker, belt, i);
            }
        }
    }

    private void processWorker(Worker worker, ConveyorBelt belt, int index) {
        if (worker.isAssembling()) {
            worker.progressAssembling();
        } else {
            pickUpComponent(worker, belt, index);
            if (worker.canMakeProduct()) {
                worker.createProduct();
            }
        }
    }

    public void pickUpComponent(Worker worker, ConveyorBelt belt, int index) {
        Item item = belt.getSlot(index).item;
        if (item instanceof Component component) {
            if (worker.getLeftHand() != component && worker.getRightHand() != component) {
                worker.takeComponent(component);
                belt.setSlot(index, null);
            }
        }
    }

    public void placeFinishedProduct(Worker worker, ConveyorBelt belt, int index) {
        if (worker.hasFinishedProduct() && belt.getSlot(index).isEmpty()) {
            belt.setSlot(index, Product.C);
            worker.setEmptyHands();
        }
    }

    public ConveyorBelt getBelt() {
        return this.belt;
    }

    public void setBelt(ConveyorBelt belt) {
        this.belt = belt;
    }

    public void printResults(ConveyorBelt belt) {
        System.out.println("Finished products: " + belt.getFinishedProducts());
        System.out.println("Unused A: " + belt.getUnusedA());
        System.out.println("Unused B: " + belt.getUnusedB());
    }
}
