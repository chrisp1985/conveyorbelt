package com.chrisp1985.conveyorbelt.objects;

import com.chrisp1985.conveyorbelt.enums.Item;

import java.util.UUID;

public class Worker {

    Item leftHand = null;
    Item rightHand = null;
    Integer workerCompletionRate = 4;
    Integer timeLeftToBuild = 0;
    String name;

    public Worker() {
        this.name = UUID.randomUUID().toString();
    }

    public void takeComponent(Item item) {
        if(leftHand == null && rightHand != item) {
            leftHand = item;
        }
        else if(rightHand == null && leftHand != item) {
            rightHand = item;
        }
    }

    public boolean canMakeProduct() {
        return leftHand != null && rightHand !=null;
    }

    public void createProduct() {
        if(canMakeProduct()) {
            leftHand = null;
            rightHand = null;
            timeLeftToBuild = workerCompletionRate;
        }
    }

    public boolean isAssembling() {
        return timeLeftToBuild > 0;
    }

    public void progressAssembling() {
        if (timeLeftToBuild > 0) {
            timeLeftToBuild--;
        }
    }

    public boolean hasFinishedProduct() {
        return timeLeftToBuild == 1;
    }

    public void setEmptyHands() {
        this.leftHand = null;
        this.rightHand = null;
    }

    public Item getLeftHand() {
        return this.leftHand;
    }

    public Item getRightHand() {
        return this.rightHand;
    }

    public void setTimeLeftToBuild(Integer timeLeftToBuild) {
        this.timeLeftToBuild = timeLeftToBuild;
    }
}
