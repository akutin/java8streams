package ru.ell.string.shortest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static ru.ell.string.Util.asSet;
import static ru.ell.string.Util.stream;

/**
 * Collects the shortest sub-sequence from a stream
 */
public class ShortestSequenceCollector<T> implements Collector<T, List<Sequence<T>>, List<T>> {
    private final static Logger LOG = LoggerFactory.getLogger(ShortestSequenceCollector.class);

    private final Set<T> filter;

    ShortestSequenceCollector(Set<T> filter) {
        this.filter = filter;
    }

    @Override
    public Supplier<List<Sequence<T>>> supplier() {
        return LinkedList::new;
    }

    @Override
    public BiConsumer<List<Sequence<T>>, T> accumulator() {
        return (matches, e) -> {
            LOG.info("Append {} to {} matches", e, matches.size());
            matches.stream()
                .filter(s -> !filter.equals(s.getMatch())) // for incomplete sequences
                .forEach(match -> {
                    LOG.info("Append {} to {}", e, match.getSequence());
                    if (filter.contains(e)) {
                        match.getMatch().add(e);
                    }
                    match.getSequence().add(e);
                });
            if (filter.contains(e)) {
                matches.add( new Sequence<>(e));
            }
        };
    }

    @Override
    public BinaryOperator<List<Sequence<T>>> combiner() {
        return (a, b) -> { throw new UnsupportedOperationException("Combiner not expected"); };
    }

    @Override
    public Function<List<Sequence<T>>, List<T>> finisher() {
        return matches -> {
            LOG.info("Finalize with {} candidate matches", matches.size());
            return matches.stream()
                .filter(s -> {
                        if (filter.equals(s.getMatch())) {
                            LOG.info("Candidate sequence {}", s.getSequence());
                            return true;
                        } else {
                            LOG.info("Drop incomplete sequence {}", s.getSequence());
                            return false;
                        }
                    }
                )
                .sorted(Comparator.comparing(Sequence::getLength))
                .map(Sequence::getSequence)
                .findFirst()
                .orElse(null);
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.noneOf(Characteristics.class);
    }

    /**
     * Instance creation
     */
    public static <T> Collector<T, ?, List<T>> shortest(Set<T> filter) {
        return new ShortestSequenceCollector<>(filter);
    }

    public static void main(String[] args) {
        assert Arrays.asList('c', 'd', 'b').equals(stream("abbacdbabc").collect(ShortestSequenceCollector.shortest(asSet('b', 'c', 'd'))));
        assert Arrays.asList('b', 'c', 'a', 'd').equals(stream("abcbcadbabc").collect(ShortestSequenceCollector.shortest(asSet('b', 'c', 'd'))));
    }
}
