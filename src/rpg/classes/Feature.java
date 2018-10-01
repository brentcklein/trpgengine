package rpg.classes;

public class Feature extends UseableObject {

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