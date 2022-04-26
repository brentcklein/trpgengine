package rpg.classes;

public class Feature extends UseableObject {

    public Feature(
            Integer id,
            String name,
            String description,
            String detailedDescription)
    {
        this.id = id;
        this.name = name.toLowerCase();
        this.description = description;
        this.detailedDescription = detailedDescription;
    }

    public Feature() {
        this(0, "","","");
    }
}