package com.company;

import java.lang.reflect.Array;
import java.util.Optional;

public class ActionSet {
    private String[] words;

    public ActionSet(String[] words) {
        this.words = words;
    }

    private Optional<String> getWord(int idx) {
        try {
            return Optional.of((String)Array.get(words, idx));
        } catch (ArrayIndexOutOfBoundsException aiob) {
            return Optional.empty();
        }
    }

    public Optional<String> getVerbOptional() { return getWord(0); }

    public Optional<String> getSubjectOptional() {
        return getWord(1);
    }

    public Optional<String> getPrepositionOptional() { return getWord(2); }

    public Optional<String> getObjectOptional() { return getWord(3); }
}
