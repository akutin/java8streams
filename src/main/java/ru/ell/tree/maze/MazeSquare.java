package ru.ell.tree.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Alexey Kutin on 10/14/2015.
 */
public class MazeSquare {
    private final String label;
    private final Collection<MazeSquare> routes;

    public MazeSquare(String label, Collection<MazeSquare> routes) {
        this.label = label;
        this.routes = new ArrayList<>(routes);
    }

    public MazeSquare(String label, MazeSquare... routes) {
        this( label, Arrays.asList( routes));
    }

    public String getLabel() {
        return label;
    }

    public Collection<MazeSquare> getRoutes() {
        return routes;
    }

    @Override
    public String toString() {
        return "[" + label + "]";
    }
}
