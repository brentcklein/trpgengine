package rpg.classes;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import org.reflections.Reflections;

import static java.util.Optional.ofNullable;


public class Main {

    // TODO: clean up this rat's nest
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Optional<State> stateOptional = Optional.empty();
        ArrayList<Room> roomArrayList = new ArrayList<>();

        String filename = "config.json";

        if (args.length > 0 && args[0].equalsIgnoreCase("example")) {
            filename = "example.config.json";
        } else if (args.length > 0 && args[0].equalsIgnoreCase("simple")) {
            filename = "simple.example.config.json";
        }

        // parse json config here
        Gson g = new Gson();
        JsonParser j = new JsonParser();

        Reflections reflections = new Reflections("rpg.custom");

        // TODO: figure out how to treat all custom classes the same. Implement a common interface?
        Set<Class<? extends CustomRoom>> roomClasses = reflections.getSubTypesOf(CustomRoom.class);
        Set<Class<? extends CustomFeature>> featureClasses = reflections.getSubTypesOf(CustomFeature.class);
        Set<Class<? extends CustomItem>> itemClasses = reflections.getSubTypesOf(CustomItem.class);

        File file = new File("config/"+filename);
        try (FileReader fr = new FileReader(file)) {
            JsonObject object = j.parse(fr).getAsJsonObject();

            for (JsonElement room :
                    object.getAsJsonArray("rooms")) {

                JsonObject roomAsJsonObject = room.getAsJsonObject();

                Set<Feature> features = new HashSet<>();

                if (roomAsJsonObject.getAsJsonArray("features") != null) {
                    for (JsonElement jsonFeature:
                            roomAsJsonObject.getAsJsonArray("features")) {
                        JsonObject featureAsJsonObject = jsonFeature.getAsJsonObject();
                        // TODO: wrap gson library to always return Optionals
                        if (ofNullable(featureAsJsonObject.get("isItem"))
                                .map(JsonElement::getAsBoolean).orElse(false)) {

                            // TODO: Refactor class handling into separate class
                            Class<? extends Item> itemClass = ofNullable(featureAsJsonObject.get("customClass"))
                                    .map(m -> g.fromJson(m,String.class))
                                    .<Class<? extends Item>>flatMap(s -> itemClasses.stream()
                                            .filter(i -> s.equalsIgnoreCase(i.getSimpleName()))
                                            .findFirst())
                                    .orElse(Item.class);
                            features.add(g.fromJson(jsonFeature, itemClass));
                        } else {
                            // TODO: Refactor class handling into separate class
                            Class<? extends Feature> featureClass = ofNullable(featureAsJsonObject.get("customClass"))
                                    .map(m -> g.fromJson(m,String.class))
                                    .<Class<? extends Feature>>flatMap(s -> featureClasses.stream()
                                            .filter(i -> s.equalsIgnoreCase(i.getSimpleName()))
                                            .findFirst())
                                    .orElse(Feature.class);
                            features.add(g.fromJson(jsonFeature, featureClass));
                        }
                    }
                }

                Boolean isEnd = ofNullable(roomAsJsonObject.get("isEnd"))
                        .map(JsonElement::getAsBoolean).orElse(false);

                HashMap<String,Integer> exits;
                Type type = new TypeToken<HashMap<String,Integer>>(){}.getType();

                // TODO: Error checking
                exits = Optional.ofNullable(roomAsJsonObject.get("exits"))
                        .map(rjo -> g.<HashMap<String,Integer>>fromJson(rjo,type))
                        .orElse(new HashMap<>());

                Class<? extends Room> roomClass = ofNullable(roomAsJsonObject.get("customClass"))
                        .map(m -> g.fromJson(m,String.class))
                        .<Class<? extends Room>>flatMap(s -> roomClasses.stream()
                                .filter(i -> s.equalsIgnoreCase(i.getSimpleName()))
                                .findFirst())
                        .orElse(Room.class);

                // TODO: this....better
                try {
                    Constructor constructor = roomClass.getConstructor(
                            Integer.class,
                            String.class,
                            String.class,
                            Set.class,
                            Map.class,
                            boolean.class,
                            boolean.class);

                    // TODO: don't cast
                    roomArrayList.add((Room)constructor.newInstance(
                            roomAsJsonObject.get("id").getAsInt(),
                            roomAsJsonObject.get("description").getAsString(),
                            roomAsJsonObject.get("detailedDescription").getAsString(),
                            features,
                            exits,
                            false,
                            isEnd
                    ));
                } catch (NoSuchMethodException nsm) {
                    System.out.println(nsm.getMessage());
                    System.exit(6);
                } catch (InstantiationException ie) {
                    System.exit(7);
                } catch (IllegalAccessException iae) {
                    System.exit(8);
                } catch (InvocationTargetException ite) {
                    System.exit(9);
                }
            }

            stateOptional = Optional.of(new State(object.get("start").getAsInt(), roomArrayList.toArray(new Room[0])));
        } catch (IOException ioee) {
            System.exit(1);
        }

        if (!stateOptional.isPresent()) {
            System.exit(1);
        }
        State s = stateOptional.get();

        while (!s.gameOver) {
            System.out.println(s.currentRoom.getDescription());
            Map<String,Integer> exits = s.currentRoom.getExits();

            for (String direction :
                    exits.keySet()) {
                System.out.println("There is an exit to the " + direction.toUpperCase());
            }

            s.gameOver = s.currentRoom.isEnd();

            if (!s.gameOver) {
                String input = sc.nextLine();

                Optional<ActionSet> actionSetOptional = InputParser.parseInput(input);

                actionSetOptional.ifPresentOrElse(
                        (actionSet -> {
                            Optional<Feature> optionalFeature =
                                    s.currentRoom.getFeatures().stream().filter(
                                            (f) -> f.getName().equals(actionSet.getSubjectOptional().get())).findAny();
                            Optional<Item> optionalItem = optionalFeature.filter((f) -> f instanceof Item).map((f) -> (Item)f);
                            Optional<Item> optionalInventoryItem = s.player.getItem(actionSet.getSubjectOptional().get());
                            switch (actionSet.getVerbOptional().get()) {
                                case "go":
                                case "head":
                                case "travel":

                                    actionSet.getSubjectOptional()
                                            .flatMap((x) -> {
                                                return s.currentRoom.getAdjacentRoom(x);
                                            })
                                            .flatMap((x) -> {
                                                return s.getRoomOptional(x);
                                            })
                                            .ifPresentOrElse(
                                                    (d) -> d.goTo(actionSet, s),
                                                    () -> System.out.println("There is not an exit in that direction."));
                                    break;
                                case "look":
                                case "examine":
                                    if (
                                            actionSet.getSubjectOptional().get().equalsIgnoreCase("room") ||
                                                    actionSet.getSubjectOptional().get().equalsIgnoreCase("around")) {
                                        s.currentRoom.look(actionSet, s);
                                    } else {
                                        optionalFeature
                                                .or(() -> optionalInventoryItem)
                                                .ifPresentOrElse(
                                                        o -> o.look(actionSet,s),
                                                        () -> System.out.println("You don't see anything interesting."));
                                    }
                                    break;
                                case "take":
                                    optionalItem.ifPresentOrElse(
                                            (item -> item.take(actionSet,s)),
                                            () -> System.out.println("You can't take that."));
                                    break;
                                case "use":
                                    optionalInventoryItem.ifPresentOrElse(
                                            (item -> item.use(actionSet,s)),
                                            () -> System.out.println("You don't have a " + actionSet.getSubjectOptional().get() + "."));
                                    break;
                                case "drop":
                                    optionalInventoryItem.ifPresentOrElse(
                                            (item -> item.drop(actionSet,s)),
                                            () -> System.out.println("You don't have a " + actionSet.getSubjectOptional().get() + "."));
                                    break;
                                default:
                                    // How do we make sure we don't run into collisions between the names of items and features?
                                    optionalFeature.or(() -> optionalInventoryItem)
                                            .ifPresentOrElse(
                                                    (feature -> feature.use(actionSet,s)),
                                                    () -> System.out.println("You can't do that."));
                                    break;
                            }
                        }),
                        () -> System.out.println("You can't do that."));
            }
        }

        System.out.println("\n\n\nGame Over!");
    }
}