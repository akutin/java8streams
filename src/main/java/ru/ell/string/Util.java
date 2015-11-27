package ru.ell.string;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Convenience to work with strings as streams
 */
public class Util {
    public static Stream<Character> stream(String in) {
        return stream(in.chars());
    }

    public static Stream<Character> stream(IntStream in) {
        return in.mapToObj( x -> (char) x);
    }

    public static <T> Set<T> asSet(T... e) {
        return new HashSet(Arrays.asList(e));
    }
}
