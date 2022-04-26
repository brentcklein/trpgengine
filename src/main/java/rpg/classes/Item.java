package rpg.classes;

import java.util.Optional;

public class Item extends Feature {
    public Item(Integer id, String name, String description, String longDescription) {
        super(id, name, description, longDescription);
    }

    public void take(ActionSet actionSet, State s) {
        s.player.addItem(this);
        s.currentRoom.removeFeature(this);
        System.out.println("You took the " + getName() + ".");
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
