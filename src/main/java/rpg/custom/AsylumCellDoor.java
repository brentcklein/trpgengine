package rpg.custom;

import rpg.classes.*;

import java.util.Map;
import java.util.Optional;

public class AsylumCellDoor extends CustomFeature {
    public AsylumCellDoor(Integer id, String name, String description, String detailedDescription) {
        super(id, name, description, detailedDescription);
    }

    @Override
    public void use(ActionSet actionSet, State s) {
        if (actionSet.getVerbOptional().get().equals("unlock")) {
            Optional<Item> keyOptional = s.player.getItemById(1);
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
        if (item.getId().equals(1)) {
            System.out.println("You slide the key into the lock and turn it. There's an audible click, and the " +
                    "door swings open.");
            s.currentRoom.addExits(Map.of("west", 2));
            s.currentRoom.removeFeature(this);
            s.currentRoom.setDetailedDescription("Light is coming through a hole in the ceiling. " +
              "To the west there's an open door.");
        } else {
            super.useWith(item, actionSet, s);
        }
    }
}
