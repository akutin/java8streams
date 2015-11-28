package ru.ell.log;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Users that visited detail pages on M out of N days
 */
public class PageVisits {

    public static Set<String> filter(List<List<String>> visits, int min) {
        return visits.stream().parallel()
            .flatMap(l -> l.stream().distinct().collect(
                Collectors.toMap(UnaryOperator.identity(), src -> System.identityHashCode(l))).entrySet().stream()
            )
            .collect(Collectors.groupingBy(
                Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toSet()))
            ).entrySet().stream()
            .filter(e -> e.getValue().size() >= min)
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        final Set<String> out = filter(
            Arrays.asList(
                Arrays.asList("1","2","3","2"),
                Arrays.asList("0","2","2","4"),
                Arrays.asList("4","5","5","4")
            ),
            2
        );
        assert out.equals( new HashSet<>(Arrays.asList("2","4")));
    }
}
