package rpg.classes;

public class Feature extends NamedObject{

    public Feature(
            String name,
            String description,
            String detailedDescription)
    {
        this.name = name.toLowerCase();
        this.description = description;
        this.detailedDescription = detailedDescription;
    }
}