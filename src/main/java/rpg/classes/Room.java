package rpg.classes;

import java.util.*;

public class Room {

    private Integer id;
    private String description;
    private String detailedDescription;
    private Set<Feature> features;
    private Map<String,Integer> exits;
    private boolean visited;
    private boolean isEnd;

    public Room(Integer id, String description, String detailedDescription, Set<Feature> features, Map<String, Integer> exits, boolean visited, boolean isEnd) {
        this.id = id;
        this.description = description;
        this.detailedDescription = detailedDescription;
        this.features = features;
        this.exits = exits;
        this.visited = visited;
        this.isEnd = isEnd;
    }

    public Room(Integer id, String description, String detailedDescription, Map<String, Integer> exits) {
        this(id, description, detailedDescription, new HashSet<>(), exits, false, false);
    }

    public Room(Integer id, String description, String detailedDescription, boolean isEnd) {
        this(id, description, detailedDescription, new HashSet<>(), new HashMap<>(), false, isEnd);
    }

    public Room(Integer id, String description, String detailedDescription) {
        this(id, description, detailedDescription, false);
    }

    public Room() {
        this(0, "", "", new HashSet<>(), new HashMap<>(), false, false);
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailedDescription() {
        StringBuilder featureDescriptions = new StringBuilder();
        for (Feature feature :
                features) {

            featureDescriptions.append(" ").append(feature.getDescription());
        }
        return detailedDescription + featureDescriptions.toString();
    }

    public Optional<Feature> getFeature(String name) {
        return getFeatures().stream().filter(
                        (f) -> f.getName().equals(name)).findAny();
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public void addFeature(Feature feature) {
        features.add(feature);
    }

    public void removeFeature(Feature feature) {
        features.remove(feature);
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisited() {
        return visited;
    }

    public Map<String, Integer> getExits() {
        return exits;
    }

    public void addExits(Map<String, Integer> rooms) {
        exits.putAll(rooms);
    }

    public void removeExit(String name) { exits.remove(name); }

    public void look(ActionSet actionSet, State s) {
        System.out.println(getDetailedDescription());
    }

    public Optional<Integer> getAdjacentRoom(String direction) {
        direction = direction.toLowerCase();
        return Optional.ofNullable(exits.get(direction));
    }

    public void goTo(ActionSet actionSet, State s) {
        setVisited(true);
        s.currentRoom = this;
    }
}
