package rpg.classes;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

public class InputParser {

    public static Optional<ActionSet> parseInput(String input) {

        ActionSet actionSet = new ActionSet(getWords(input));
        String[] directions = {"north", "south", "east", "west", "up", "down"};

        if (actionSet.getVerbOptional().isPresent() &&
                ((actionSet.getVerbOptional().get().equals("look") || actionSet.getVerbOptional().get().equals("examine")) &&
                !actionSet.getSubjectOptional().isPresent())) {
            String[] words = new String[2];
            Array.set(words,0, actionSet.getVerbOptional().get());
            Array.set(words,1,"room");
            return Optional.of(new ActionSet(words));
        } else if (actionSet.getVerbOptional().isPresent() &&
                !actionSet.getSubjectOptional().isPresent() &&
                Arrays.stream(directions).anyMatch(actionSet.getVerbOptional().get()::equals)) {
            String[] words = new String[2];
            Array.set(words, 0, "go");
            Array.set(words, 1, actionSet.getVerbOptional().get());
            return Optional.of(new ActionSet(words));
        } else if (actionSet.getSubjectOptional().isPresent()) {
            return Optional.of(actionSet);
        } else {
            return Optional.empty();
        }
    }

    private static String[] getWords(String input) {
        return Arrays.stream(input.split("\\s+")).map(String::toLowerCase).toArray(String[]::new); // what does String[]::new mean?
    }
}
