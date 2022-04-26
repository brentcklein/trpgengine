package rpg.custom;

import rpg.classes.ActionSet;
import rpg.classes.CustomFeature;
import rpg.classes.State;

public class MapStand extends CustomFeature {
    public MapStand(Integer id, String name, String description, String detailedDescription) {
        super(id, name, description, detailedDescription);
    }

    @Override
    public void use(ActionSet actionSet, State s) {
        if (actionSet.getVerbOptional().get().equalsIgnoreCase("compliment")) {
            System.out.println("The map really needed that today.");
        } else {
            super.use(actionSet, s);
        }
    }
}
