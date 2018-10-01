package rpg.classes;

public abstract class NamedObject {

    protected String name;
    protected String description;
    protected String detailedDescription;

    public void setName(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailedDescription() { return detailedDescription; }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public void look(ActionSet actionSet, State s) {
        System.out.println(getDetailedDescription());
    }
}
