package rpg.classes;

public class UseableObject extends NamedObject {

    protected boolean activated = false;

    public void use(ActionSet actionSet, State s) {
        System.out.println("Nothing happens.");
    }

    public void useWith(Item item, ActionSet actionSet, State s) {
        System.out.println("Nothing happens.");
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) { this.activated = activated; }
}
