package rpg.classes;

public class Feature {
    private String name;
    private String description;
    private String detailedDescription;
    private boolean activated = false;

    public Feature(
            String name,
            String description,
            String detailedDescription)
    {
        this.name = name.toLowerCase();
        this.description = description;
        this.detailedDescription = detailedDescription;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void look(ActionSet actionSet, State s) {
        System.out.println(getDetailedDescription());
    }

    public void use(ActionSet actionSet, State s) {
        System.out.println("Nothing happens.");
    }

    public void useWith(Item item, ActionSet actionSet, State s) {
        System.out.println("Nothing happens.");
    }
}