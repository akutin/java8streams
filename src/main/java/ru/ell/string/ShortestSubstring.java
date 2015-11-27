package ru.ell.string;

import java.util.*;

/**
 * Finds a shortest substring that contains all given characters
 */
public class ShortestSubstring {
    public static String shortestSubstring(String in, Set<Character> filter) {
        final List<Substring> substring = new LinkedList<>();
        for(int i = 0; i<in.length(); i++) {
            final Character c = in.charAt(i);
            substring.stream().filter(s -> !filter.equals(s.found)).forEach(s -> {
                s.sub.append(c);
            });
            if( filter.contains(c)) {
                for(int k = 0; k<substring.size(); k++) {
                    final Substring s = substring.get(k);
                    if(s.found.add(c) && filter.equals(s.found)) {
                        for( int j = k-1; j>=0; j--) {
                            final Substring check = substring.get(j);
                            if (check.sub.length() < s.sub.length()) {
                                substring.remove(k); // remove itself
                                k--;
                                break;
                            } else if( check.sub.length() > s.sub.length()) {
                                substring.remove(j); // remove anything that is longer
                                k--;
                            }
                        }
                    }
                }
                substring.add( new Substring(c));
            }
        }

        return substring.stream().filter( s -> filter.equals(s.found))
                .sorted( (a,b) -> a.sub.length() - b.sub.length())
                .map( Substring::getMatch)
                .findFirst()
                .orElse( null);
    }

    public static void main(String[] args) {
        assert shortestSubstring("abcbcdab", new HashSet<>( Arrays.asList( 'b', 'c', 'd'))) == "bcd";
        assert shortestSubstring("abcbcadbabc", new HashSet<>( Arrays.asList( 'b', 'c', 'd'))) == "bcad";
    }

    static class Substring {
        final Set<Character> found = new HashSet<>();
        final StringBuilder sub = new StringBuilder();

        Substring(Character c) {
            found.add(c);
            sub.append(c);
        }

        public String getMatch() {
            return sub.toString();
        }

        @Override
        public String toString() {
            return "Substring{" +
                    "found=" + found +
                    ", sub=" + sub +
                    '}';
        }
    }
}
