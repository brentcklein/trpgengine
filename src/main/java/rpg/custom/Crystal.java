package rpg.custom;

import rpg.classes.ActionSet;
import rpg.classes.CustomItem;
import rpg.classes.State;

public class Crystal extends CustomItem {
    public Crystal(String name, String description, String longDescription) {
        super(name, description, longDescription);
    }

    @Override
    public void drop(ActionSet actionSet, State s) {
        System.out.println("You drop the crystal and it shatters on the ground.");
        s.player.removeItem(this);
    }

    @Override
    public void use(ActionSet actionSet, State s) {
        if (
                (actionSet.getVerbOptional().get().equals("use") ||
                        actionSet.getVerbOptional().get().equals("put") ||
                        actionSet.getVerbOptional().get().equals("place") ||
                        actionSet.getVerbOptional().get().equals("set")) &&
                        actionSet.getObjectOptional().isPresent())
        {
            this.useOn(actionSet, s);
        } else {
            super.use(actionSet, s);
        }
    }
}
