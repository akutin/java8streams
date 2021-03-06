package ru.ell.string;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * String matches count with streams
 */
public class Counter {
    /**
     * Counts number of character occurrences in a string
     */
    public static long countOf(String s, Character c) {
        return Util.stream(s.chars()).filter(x -> c.equals(x)).count();
    }

    /**
     * Counts occurrences of a set of characters in a string
     *
     * Matches are case-insensitive
     */
    public static Map<Character, Long> countOf(String s, Set<Character> c) {
        return countOf(Util.stream(s.chars()), c);
    }

    public static Map<Character, Long> countOf(Stream<Character> s, Set<Character> c) {
        final Set<Character> caseInsensitive = c.stream().map(Character::toLowerCase).collect(Collectors.toSet());

        final Map<Character, Long> counts = s.map( Character::toLowerCase)
                .filter(x -> caseInsensitive.contains(x))
                .collect(Collectors.groupingBy(UnaryOperator.identity(), Collectors.counting()));

        caseInsensitive.stream().forEach( match -> counts.merge( match, 0l, Long::sum));

        return counts;
    }

    public static IntStream steam(Reader reader) {
        final PrimitiveIterator.OfInt it=new PrimitiveIterator.OfInt() {
            private int last=-2; // not read

            public int nextInt() {
                if(last==-2 && !hasNext())
                    throw new NoSuchElementException();
                try {
                    return last;
                } finally {
                    last=-2;
                }
            }

            public boolean hasNext() {
                if(last==-2) {
                    try {
                        last = reader.read();
                    } catch (IOException ex) {
                        throw new UncheckedIOException(ex);
                    }
                }
                return last>=0;
            }
        };

        return StreamSupport.intStream(
                Spliterators.spliteratorUnknownSize(it, Spliterator.ORDERED | Spliterator.IMMUTABLE | Spliterator.NONNULL),
                false
        );
    }

    public static void main(String[] args) {
        final long countOfA = countOf("Counts occurrences of a set of characters in a string", 'a');
        assert countOfA == 4;

        final Map<Character, Long> counts = countOf("Counts occurrences of a set of characters in a string", new HashSet<>( Arrays.asList( 'a', 'b' , 'c')));
        assert counts.get('a') == 4;
        assert counts.get('b') == 0;
        assert counts.get('c') == 6;

        final Map<Character, Long> sourceCounts;
        try(Reader reader = new FileReader("build.gradle")) {
             sourceCounts = countOf( Util.stream(steam(reader)),  new HashSet<>( Arrays.asList( 'a', 'b' , 'c')));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        assert sourceCounts.get('a') == 10;
        assert sourceCounts.get('b') == 1;
        assert sourceCounts.get('c') == 7;

        try(Reader reader = new FileReader("build.gradle")) {
            final StringBuilder restore = Util.stream(steam(reader)).reduce(new StringBuilder(), (b, c) -> b.append(c), (b, c) -> b.append(c));
            restore.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }
}
