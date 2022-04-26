package rpg.classes;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputParser {

    public static Optional<ActionSet> parseInput(String input) {

        ActionSet actionSet = new ActionSet(getWords(input));

        if (actionSet.getVerbOptional().isPresent() &&
                ((actionSet.getVerbOptional().get().equals("look") || actionSet.getVerbOptional().get().equals("examine")) &&
                !actionSet.getSubjectOptional().isPresent())) {
            String[] words = new String[2];
            Array.set(words,0, actionSet.getVerbOptional().get());
            Array.set(words,1,"room");
            return Optional.of(new ActionSet(words));
        } else if (
            Stream.of(
                "n", 
                "north", 
                "s", 
                "south", 
                "e", 
                "east", 
                "w", 
                "west", 
                "u", 
                "up", 
                "d", 
                "down"
            ).collect(Collectors.toCollection(HashSet::new))
            .contains(actionSet.getVerbOptional().get())) {
                String[] words = new String[2];
                Array.set(words, 0, "go");
                Array.set(words, 1, translateDirectionalShorthand(actionSet.getVerbOptional().get()));
                return Optional.of(new ActionSet(words));
            } else if (actionSet.getSubjectOptional().isPresent()) {
            return Optional.of(actionSet);
        } else if (actionSet.getVerbOptional().get().equals("i") || actionSet.getVerbOptional().get().equals("inventory")) {
            String[] words = new String[2];
            Array.set(words,0,"look");
            Array.set(words,1, "inventory");
            return Optional.of(new ActionSet(words));
        } else {
            return Optional.empty();
        }
    }

    private static String translateDirectionalShorthand(String direction) {
        switch (direction) {
            case "n":
                return "north";
            case "s":
                return "south";
            case "e":
                return "east";
            case "w":
                return "west";
            case "u":
                return "up";
            case "d":
                return "down";
            default:
                return direction;
        }
    }

    private static String[] getWords(String input) {
        return Arrays.stream(input.split("\\s+")).map(String::toLowerCase).toArray(String[]::new); // what does String[]::new mean?
    }
}
