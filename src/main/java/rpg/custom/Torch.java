package rpg.custom;

import rpg.classes.ActionSet;
import rpg.classes.CustomItem;
import rpg.classes.Feature;
import rpg.classes.State;

import java.util.Optional;

public class Torch extends CustomItem {
    public Torch(Integer id, String name, String description, String longDescription) {
        super(id, name, description, longDescription);
    }

    @Override
    public void use(ActionSet actionSet, State s) {
        if (actionSet.getVerbOptional().get().equals("light")) {
            if (isActivated()) {
                System.out.println("It's already lit.");
            } else {
                Optional<Feature> brazierOptional = s.currentRoom.getFeature("brazier");
                if (brazierOptional.isPresent()) {
                    brazierOptional.get().useWith(this, actionSet, s);
                } else {
                    System.out.println("You don't have anything to light it with.");
                }
            }
        } else {
            Optional<String> objectOptional = actionSet.getObjectOptional();
            if (objectOptional.isPresent()) {
                this.useOn(actionSet, s);
            } else {
                super.use(actionSet, s);
            }
        }
    }

    @Override
    public void look(ActionSet actionSet, State s) {
        if (!isActivated()) {
            super.look(actionSet, s);
        } else {
            System.out.println("The lit torch gives off bright light.");
        }
    }
}
