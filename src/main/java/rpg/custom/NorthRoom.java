package rpg.custom;

import rpg.classes.ActionSet;
import rpg.classes.CustomRoom;
import rpg.classes.Feature;
import rpg.classes.State;

import java.util.Map;
import java.util.Set;

public class NorthRoom extends CustomRoom {
    public NorthRoom(Integer id, String description, String detailedDescription, Set<Feature> features, Map<String, Integer> exits, boolean visited, boolean isEnd) {
        super(id,description,detailedDescription,features,exits,visited,isEnd);
    }

    @Override
    public void look(ActionSet actionSet, State s) {
        System.out.println("You can't see anything.");
    }
}
