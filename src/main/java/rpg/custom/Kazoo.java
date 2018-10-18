package rpg.custom;

import rpg.classes.ActionSet;
import rpg.classes.CustomItem;
import rpg.classes.State;

public class Kazoo extends CustomItem {
    public Kazoo(String name, String description, String longDescription) {
        super(name, description, longDescription);
    }

    @Override
    public void use(ActionSet actionSet, State s) {
        if (
                actionSet.getVerbOptional().get().equalsIgnoreCase("play") ||
                actionSet.getVerbOptional().get().equalsIgnoreCase("use")) {
            System.out.println("doot");
            s.currentRoom = s.getRoomOptional(1).get();
            if (!isActivated()) {
                setActivated(true);
            } else {
                System.out.println("The kazoo broke!");
                s.player.removeItem(this);
            }
        } else {
            super.use(actionSet, s);
        }
    }
}
