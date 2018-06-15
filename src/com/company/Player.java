package com.company;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Player {
    private Set<Item> inventory;

    public Player() {
        inventory = new HashSet<>();
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) { inventory.remove(item); }

    public Set<Item> getInventory() {
        return inventory;
    }

    public Optional<Item> getItem (String itemName) {
        return inventory.stream().filter((f) -> f.getName().equals(itemName)).findAny();
    }
}
