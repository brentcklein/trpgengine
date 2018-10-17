package rpg.custom;

import rpg.classes.Item;

public abstract class CustomItem extends Item {
    public CustomItem(String name, String description, String longDescription) {
        super(name, description, longDescription);
    }
}
