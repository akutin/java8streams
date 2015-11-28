package ru.ell.log;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Users that visited detail pages on M out of N days
 */
public class PageVisits {
    static class PageHit {
        final UUID file;
        final String cid;

        public PageHit(UUID file, String cid) {
            this.file = file;
            this.cid = cid;
        }

        public UUID getFile() {
            return file;
        }

        public String getCID() {
            return cid;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PageHit pageHit = (PageHit) o;

            if (!file.equals(pageHit.file)) return false;
            return cid.equals(pageHit.cid);
        }

        @Override
        public int hashCode() {
            int result = file.hashCode();
            result = 31 * result + cid.hashCode();
            return result;
        }
    }

    public static Set<String> filter(List<List<String>> visits, int min) {
        return visits.stream().parallel()
                .flatMap( l -> {
                    final UUID fileId = UUID.randomUUID();
                    return l.stream().map( cid -> new PageHit(fileId, cid)).collect(Collectors.toSet()).stream();
                }).collect(
                        Collectors.groupingBy(
                                PageHit::getCID,
                                Collectors.mapping(PageHit::getFile, Collectors.toSet())
                        )).entrySet().stream()
                .filter(e -> e.getValue().size() >= min)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        final Set<String> out = filter(Arrays.asList(
                Arrays.asList("1","2","3","2"),
                Arrays.asList("0","2","2","4"),
                Arrays.asList("4","5","5","4")
            ),
            2
        );
        assert out.equals( new HashSet<>(Arrays.asList("2","4")));
    }
}
