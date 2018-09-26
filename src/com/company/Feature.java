package com.company;

public class Feature {
    private String name;
    private String description;
    private String detailedDescription;
    private boolean activated = false;
    private Actionable actionable;

    public Feature(
            String name,
            String description,
            String detailedDescription
    ) {
        this(name, description, detailedDescription, (feature, actionSet, state) -> { feature.getDetailedDescription(); });
    }

    public Feature(
            String name,
            String description,
            String detailedDescription,
            Actionable actionable)
    {
        this.name = name.toLowerCase();
        this.description = description;
        this.detailedDescription = detailedDescription;
        this.actionable = actionable;
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

    }

    private void act(ActionSet actionSet, State s) {
        System.out.println("Nothing happens.");
    }

    public void useWith(Item item, ActionSet actionSet, State s) {
        System.out.println("Nothing happens.");
    }

    public void takeAction(ActionSet actionSet, State state) {
        // switch on the user input verb etc
        //take default look action
        // take default go action
        // default use action
        actionable.doStuff(this, actionSet, state);
    }
}


@FunctionalInterface
interface Actionable {
    void doStuff(Feature feature, ActionSet actionSet, State state);
}
