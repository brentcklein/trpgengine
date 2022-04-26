package rpg.classes;

import java.util.Map;
import java.util.Set;

public abstract class CustomRoom extends Room {

    public CustomRoom(Integer id, String description, String detailedDescription, Set<Feature> features, Map<String, Integer> exits, boolean visited, boolean isEnd) {
        super(id,description,detailedDescription,features,exits,visited,isEnd);
    }

}
