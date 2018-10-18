package rpg.custom;

import rpg.classes.ActionSet;
import rpg.classes.CustomFeature;
import rpg.classes.Item;
import rpg.classes.State;

public class Brazier extends CustomFeature {
    public Brazier(String name, String description, String detailedDescription) {
        super(name, description, detailedDescription);
    }

    @Override
    public void useWith(Item item, ActionSet actionSet, State s) {
        if (item.getName().equals("torch")) {
            System.out.println("You light the torch in the brazier.");
            item.setActivated(true);
        } else {
            super.useWith(item, actionSet, s);
        }
    }
}
