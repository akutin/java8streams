package ru.ell.string;


import static ru.ell.string.SlidingCollector.sliding;

/**
 * Produces N-grams from a character stream
 */
public class NGrams {

    public static void main(String[] args) {
        final String input = "INTEL CORE I7-3770T, 2.5GHZ, LGA1155 ,8MB, 4 CORES/8 THREADS, 45W, MAX MEMORY - 32GB [CM8063701212200]";

        Counter.chars(input.chars())
                .filter(Character::isLetterOrDigit)
                .map(Character::toLowerCase)
                .collect(sliding(4)).stream().map(x ->
                        x.stream().reduce( new StringBuilder(), StringBuilder::append, StringBuilder::append)
                )
                .forEach(System.out::println);
    }
}
