package ru.ell.string.shortest;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Matching sequence
 */
class Sequence<T> {
    /**
     * Matched items in the sequence
     */
    private final Set<T> match = new HashSet<>();

    /**
     * Accumulated sequence
     */
    private final List<T> sequence = new LinkedList<>();

    Sequence(T e) {
        match.add(e);
        sequence.add(e);
    }

    public Set<T> getMatch() {
        return match;
    }

    public List<T> getSequence() {
        return sequence;
    }

    public int getLength() {
        return sequence.size();
    }
}
