package rpg.custom;

import rpg.classes.*;

import java.util.Map;
import java.util.Optional;

public class Door extends CustomFeature {
    public Door(String name, String description, String detailedDescription) {
        super(name, description, detailedDescription);
    }

    @Override
    public void use(ActionSet actionSet, State s) {
        if (actionSet.getVerbOptional().get().equals("unlock")) {
            Optional<Item> keyOptional = s.player.getItem("key");
            if (keyOptional.isPresent()) {
                this.useWith(keyOptional.get(), actionSet, s);
            } else {
                System.out.println("You don't have anything to unlock it with.");
            }
        } else {
            super.use(actionSet, s);
        }
    }

    @Override
    public void useWith(Item item, ActionSet actionSet, State s) {
        if (item.getName().equals("key")) {
            System.out.println("You slide the key into the lock and turn it. There's an audible click, and the " +
                    "door swings open.");
            s.currentRoom.addExits(Map.of("east", 3));
            s.currentRoom.removeFeature(this);
            s.currentRoom.setDetailedDescription("Light is coming through a hole in the ceiling. To the west a " +
                    "flight of stairs leads downward. To the east there's an open door.");
        } else {
            super.useWith(item, actionSet, s);
        }
    }
}
