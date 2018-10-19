package rpg.classes;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;

import static java.util.Optional.ofNullable;

public class WorldBuilder {
    public static Optional<State> getNewState(String filename) {
        Optional<State> stateOptional = Optional.empty();

        try (FileReader fr = new FileReader(new File("config/"+filename))) {
            JsonObject configObject = getAsJsonObject(fr);

            ArrayList<Room> roomArrayList = getRoomsFromArray(configObject.getAsJsonArray("rooms"));

            stateOptional = Optional.of(new State(configObject.get("start").getAsInt(), roomArrayList.toArray(new Room[0])));
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            System.exit(1);
        }

        return stateOptional;
    }

    private static ArrayList<Room> getRoomsFromArray(JsonArray jsonArray) {
        Gson g = new Gson();
        ArrayList<Room> ret = new ArrayList<>();
        for (JsonElement r :
                jsonArray) {

            JsonObject room = r.getAsJsonObject();

            Set<Feature> features = new HashSet<>();

            Optional.ofNullable(room.getAsJsonArray("features"))
                    .ifPresent((array) -> {
                        for (JsonElement f :
                                array) {
                            JsonObject feature = f.getAsJsonObject();

                            // TODO: wrap gson library to always return Optionals

                            Class<?> defaultClass;
                            if (ofNullable(feature.get("isItem"))
                                    .map(JsonElement::getAsBoolean).orElse(false)) {
                                defaultClass = Item.class;
                            } else {
                                defaultClass = Feature.class;
                            }

                            Class<?> itemClass = getCustomClassForObject(feature, defaultClass);

                            // TODO: Revisit the typing here? Is this safe?
                            features.add(g.<Feature>fromJson(f, itemClass));
                        }
                    });

            Boolean isEnd = ofNullable(room.get("isEnd"))
                    .map(JsonElement::getAsBoolean).orElse(false);

            HashMap<String,Integer> exits;
            Type type = new TypeToken<HashMap<String,Integer>>(){}.getType();

            // TODO: Error checking
            exits = Optional.ofNullable(room.get("exits"))
                    .map(e -> g.<HashMap<String,Integer>>fromJson(e,type))
                    .orElse(new HashMap<>());

            Class<?> roomClass = getCustomClassForObject(room, Room.class);

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

                // TODO: don't cast?
                ret.add((Room)constructor.newInstance(
                        room.get("id").getAsInt(),
                        room.get("description").getAsString(),
                        room.get("detailedDescription").getAsString(),
                        features,
                        exits,
                        false,
                        isEnd
                ));
                // TODO: implement logging and sane error handling
            } catch (NoSuchMethodException nsm) {
                System.err.println(nsm.getMessage());
                System.exit(6);
            } catch (InstantiationException ie) {
                System.exit(7);
            } catch (IllegalAccessException iae) {
                System.exit(8);
            } catch (InvocationTargetException ite) {
                System.exit(9);
            }
        }

        return ret;
    }

    private static JsonObject getAsJsonObject(FileReader fr) {
        JsonParser j = new JsonParser();

        return j.parse(fr).getAsJsonObject();
    }

    private static Class<?> getCustomClassForObject(JsonObject object, Class<?> ancestor) {
        Gson g = new Gson();
        return ofNullable(object.get("customClass"))
                .map(m -> g.fromJson(m,String.class))
                .<Class<?>>map(s -> {
                    try {
                        Class<?> c = Class.forName("rpg.custom." + s);
                        if (!ancestor.isAssignableFrom(c)) {
                            throw new ClassNotFoundException();
                        } else {
                            return c.asSubclass(ancestor);
                        }
                    } catch (ClassNotFoundException cnf) {
                        return null;
                    }
                })
                .orElse(ancestor);
    }
}
