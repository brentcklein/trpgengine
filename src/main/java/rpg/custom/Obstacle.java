package rpg.custom;

import rpg.classes.ActionSet;
import rpg.classes.CustomFeature;
import rpg.classes.State;

import java.util.Map;

public class Obstacle extends CustomFeature {
    public Obstacle(Integer id, String name, String description, String detailedDescription) {
        super(id, name, description, detailedDescription);
    }

    @Override
    public void use(ActionSet actionSet, State s) {
        if (
                actionSet.getVerbOptional().get().equals("move") ||
                        actionSet.getVerbOptional().get().equals("push")
        ) {
            s.currentRoom.addExits(Map.of("north", 9));
            System.out.println("You move the obstacle to the side.");
            setDetailedDescription("It's not really much of an obstacle anymore.");
            s.currentRoom.setDetailedDescription("The broken remains of a wooden cot stand against one wall. " +
                    "There's a small hole in the far wall you could probably crawl through.");
        } else {
            super.use(actionSet, s);
        }
    }
}
