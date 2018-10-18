package rpg.custom;

import rpg.classes.ActionSet;
import rpg.classes.CustomRoom;
import rpg.classes.Feature;
import rpg.classes.State;

import java.util.Map;
import java.util.Set;

public class Oubliette extends CustomRoom {
    public Oubliette(Integer id, String description, String detailedDescription, Set<Feature> features, Map<String, Integer> exits, boolean visited, boolean isEnd) {
        super(id, description, detailedDescription, features, exits, visited, isEnd);
    }

    @Override
    public void goTo(ActionSet actionSet, State s) {
        if (!s.player.getItem("kazoo").isPresent()) {
            setEnd(true);
            System.out.println(getDetailedDescription());
        } else {
            setDetailedDescription("Faint torchlight is coming through a rough hole in the ceiling. There are " +
                    "no other openings.");
        }
        super.goTo(actionSet, s);
    }
}
