package com.chrisp1985.conveyorbelt.services;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "conveyor.belt")
public class ConveyorBeltServiceConfig {

    private int stepCount;
    private int workerPairCount;

    public int getBeltLength() {
        return beltLength;
    }

    public void setBeltLength(int beltLength) {
        if (beltLength < 1) {
            throw new IllegalArgumentException("beltLength must be at least 1");
        }
        this.beltLength = beltLength;
    }

    private int beltLength;

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        if (stepCount < 1) {
            throw new IllegalArgumentException("stepCount must be at least 1");
        }
        this.stepCount = stepCount;
    }

    public int getWorkerPairCount() {
        return workerPairCount;
    }

    public void setWorkerPairCount(int workerPairCount) {
        if (workerPairCount < 1) {
            throw new IllegalArgumentException("workerPairCount must be at least 1");
        }
        this.workerPairCount = workerPairCount;
    }
}
