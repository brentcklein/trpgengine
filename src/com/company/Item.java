package com.company;

import java.util.Optional;
import java.util.function.Consumer;

public class Item extends Feature {
    public Item(String name, String description, String longDescription) {
        super(name, description, longDescription);
    }

    public Item(String name, String description, String detailedDescription, Actionable consumer) {
        super(name, description, detailedDescription, consumer);
    }

    public void take(ActionSet actionSet, State s) {
        s.player.addItem(this);
        s.currentRoom.removeFeature(this);
        System.out.println("You took the " + getName() + ".");
    }

    public void use(ActionSet actionSet, State s) {
        System.out.println("Nothing happens.");
    }

    public void _use(ActionSet actionSet, State s) {
        if (isPossessed(s)) {
            use(actionSet, s);
        } else {
            System.out.println("You don't have that.");
        }
    }

    public void useOn(ActionSet actionSet, State s) {
        String objectName = actionSet.getObjectOptional().get();
        Optional<Feature> objectOptional = Optional.empty();
        if (s.currentRoom.getFeature(objectName).isPresent()) {
            objectOptional = s.currentRoom.getFeature(objectName);
        } else if (s.player.getItem(objectName).isPresent()) {
            objectOptional = Optional.of(s.player.getItem(objectName).get());
        }

        objectOptional.ifPresent(feature -> feature.useWith(this, actionSet, s));
    }

    public void drop(ActionSet actionSet, State s) {
        s.currentRoom.addFeature(this);
        s.player.removeItem(this);
        setDescription("There is a " + getName() + " on the floor.");
        System.out.println("You dropped the " + getName() + ".");
    }

    public boolean isPossessed(State s) {
        return s.player.getItem(this.getName()).isPresent();
    }
}
