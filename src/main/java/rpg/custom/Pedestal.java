package rpg.custom;

import rpg.classes.ActionSet;
import rpg.classes.CustomFeature;
import rpg.classes.Item;
import rpg.classes.State;

public class Pedestal extends CustomFeature {
    public Pedestal(Integer id, String name, String description, String detailedDescription) {
        super(id, name, description, detailedDescription);
    }

    @Override
    public void useWith(Item item, ActionSet actionSet, State s) {
        if (item.getName().equals("crystal")) {
            System.out.println("You place the crystal on to the pedestal. As soon as you do, the crystals in the " +
                    "cave begin to pulse in time with the one on the pedestal. Riches are yours! The dragon is " +
                    "slain! The laundry is done! You win!");
            s.currentRoom.setEnd(true);
            s.currentRoom.setDescription("");
            s.currentRoom.setDetailedDescription("");
            s.currentRoom.removeExit("SOUTH");
        } else {
            super.useWith(item, actionSet, s);
        }
    }
}
