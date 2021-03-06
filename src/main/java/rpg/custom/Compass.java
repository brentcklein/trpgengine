package rpg.custom;

import rpg.classes.ActionSet;
import rpg.classes.CustomItem;
import rpg.classes.State;

public class Compass extends CustomItem {
    public Compass(String name, String description, String longDescription) {
        super(name, description, longDescription);
    }

    @Override
    public void drop(ActionSet actionSet, State s) {
        this.setDetailedDescription("An old compass. The face is cracked.");
        super.drop(actionSet, s);
    }
}
