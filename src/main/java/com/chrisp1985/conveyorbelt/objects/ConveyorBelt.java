package com.chrisp1985.conveyorbelt.objects;

import com.chrisp1985.conveyorbelt.enums.Component;
import com.chrisp1985.conveyorbelt.enums.Item;
import com.chrisp1985.conveyorbelt.enums.Product;

import java.util.*;

public class ConveyorBelt {
    private final List<Slot> belt;
    private final int length;
    private final Random random;
    private int finishedProducts = 0;
    private int unusedA = 0;
    private int unusedB = 0;
    private Iterator<Component> predefinedComponents = null;

    public ConveyorBelt(int length) {
        this.length = length;
        this.belt = new ArrayList<>(Collections.nCopies(length, new Slot(null)));
        this.random = new Random();
    }

    public ConveyorBelt(int lengthOfBelt, List<Component> componentList) {
        this.length = lengthOfBelt;
        this.belt = new ArrayList<>(Collections.nCopies(length, new Slot(null)));
        this.random = new Random();
        setPredefinedComponents(componentList);
    }

    public void advance() {
        Item lastItem = belt.get(length - 1).item;
        if (lastItem == Component.A) {
            unusedA++;
        }
        if (lastItem == Component.B) {
            unusedB++;
        }
        if (lastItem== Product.C) {
            finishedProducts++;
        }

        for (int i = length - 1; i > 0; i--) {
            belt.set(i, belt.get(i - 1));
        }

        addNewComponentToBelt();
    }

    private void addNewComponentToBelt() {
        Component newItem = (predefinedComponents != null && predefinedComponents.hasNext())
                ? predefinedComponents.next()
                : getRandomComponent();

        belt.set(0, new Slot(newItem));
    }

    private Component getRandomComponent() {
        int roll = random.nextInt(3);
        return (roll == 0) ? Component.A : (roll == 1) ? Component.B : null;
    }

    private void setPredefinedComponents(List<Component> componentList) {
        this.predefinedComponents = (componentList != null) ? componentList.iterator() : null;
    }

    public Slot getSlot(int index) {
        return belt.get(index);
    }

    public void setSlot(int index, Item item) {
        belt.set(index, new Slot(item));
    }

    public int getFinishedProducts() { return finishedProducts; }
    public int getUnusedA() { return unusedA; }
    public int getUnusedB() { return unusedB; }

    public int getLength() {
        return length;
    }
}
