package rpg.custom;

import rpg.classes.ActionSet;
import rpg.classes.CustomFeature;
import rpg.classes.Item;
import rpg.classes.State;

public class Rags extends CustomFeature {
    public Rags(String name, String description, String detailedDescription) {
        super(name, description, detailedDescription);
    }

    @Override
    public void look(ActionSet a, State s) {
        super.look(a, s);
        this.setDetailedDescription("An old pile of rags, probably once used as a bed.");
        if (!this.isActivated()) {
            s.currentRoom.addFeature(new Item(
                    "key",
                    "There's a key on the floor.",
                    "A small key."
            ) {
                @Override
                public void use(ActionSet actionSet, State s) {
                    if (actionSet.getObjectOptional().isPresent()) {
                        this.useOn(actionSet, s);
                    } else {
                        super.use(actionSet, s);
                    }
                }
            });
            this.setActivated(true);
        }
    }
}
