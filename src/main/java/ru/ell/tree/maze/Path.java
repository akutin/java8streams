package ru.ell.tree.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Alexey Kutin on 10/13/2015.
 */
public class Path {
    private final List<MazeSquare> path;

    public Path(MazeSquare path) {
        this(Arrays.asList(path));
    }

    public Path(List<MazeSquare> path, MazeSquare step) {
        final List<MazeSquare> nextStep = new ArrayList<>( path.size() + 1);
        nextStep.addAll( path);
        nextStep.add(step);
        this.path = nextStep;
    }

    public Path(List<MazeSquare> path) {
        this.path = path;
    }

    public List<MazeSquare> getPath() {
        return path;
    }

    public MazeSquare getCurrentSquare() {
        return path.get( path.size() - 1);
    }

    public static final WeightComparator SHORTEST_FIRST = new WeightComparator();

    public static class WeightComparator implements Comparator<Path> {
        private WeightComparator() {}

        @Override
        public int compare(Path o1, Path o2) {
            return o1.getPath().size() - o2.getPath().size();
        }
    }
}
