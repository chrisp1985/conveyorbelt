package com.chrisp1985.conveyorbelt.objects;

import com.chrisp1985.conveyorbelt.enums.Item;

public class Slot {
    public Item item;

    public Slot(Item item) {
        this.item = item;
    }

    public boolean isEmpty() {
        return item == null;
    }
}
