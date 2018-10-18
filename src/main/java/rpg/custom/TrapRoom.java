package rpg.custom;

import rpg.classes.ActionSet;
import rpg.classes.CustomRoom;
import rpg.classes.Feature;
import rpg.classes.State;

import java.util.Map;
import java.util.Set;

public class TrapRoom extends CustomRoom {
    public TrapRoom(Integer id, String description, String detailedDescription, Set<Feature> features, Map<String, Integer> exits, boolean visited, boolean isEnd) {
        super(id, description, detailedDescription, features, exits, visited, isEnd);
    }

    @Override
    public void goTo(ActionSet actionSet, State s) {
        if (!isVisited()) {
            setVisited(true);
            System.out.println(getDescription());
            s.getRoomOptional(7).get().goTo(actionSet, s);
        } else {
            setDescription("You are in one of the cells.");
            setDetailedDescription("There's a rickety table on one side of the cell. A large hole opens in the " +
                    "middle of the floor.");
            super.goTo(actionSet, s);
        }
    }
}
