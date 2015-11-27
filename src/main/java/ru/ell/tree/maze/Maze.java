package ru.ell.tree.maze;

import java.util.PriorityQueue;

/**
 * A* to solve a maze
 *
 * Based on http://stackoverflow.com/a/3097677 with added loops
 */
public class Maze {
    private static final String EXIT = "EXIT";

    public Path solve(MazeSquare maze) {
        final PriorityQueue<Path> queue = new PriorityQueue<>(Path.SHORTEST_FIRST);
        queue.offer( new Path(maze));
        while( !queue.isEmpty()) {
            final Path path = queue.remove();
            System.out.println("Evaluate: " + path.getPath());
            final MazeSquare current = path.getCurrentSquare();
            for(final MazeSquare nextStep : current.getRoutes()) {
                final Path toEvaluate = new Path(path.getPath(), nextStep);
                if( !path.getPath().contains( nextStep)) {
                    if( EXIT.equals( nextStep.getLabel())) {
                        return toEvaluate;
                    }
                    System.out.println("Add: " + toEvaluate.getPath());
                    queue.offer(toEvaluate);
                } else {
                    System.out.println( "Loop identified: " + toEvaluate.getPath());
                }
            }
        }

        throw new RuntimeException("No path through the maze");
    }

    public static void main(String[] args) {
        final MazeSquare loopSquare = new MazeSquare("L");
        final MazeSquare joinSquare = new MazeSquare("E",
                new MazeSquare("H",
                        loopSquare,
                        new MazeSquare("M",
                                new MazeSquare(EXIT),
                                new MazeSquare("O")
                        )
                ),
                new MazeSquare("I")
        );
        loopSquare.getRoutes().add(joinSquare);
        final MazeSquare entrance = new MazeSquare("A",
                new MazeSquare("C",
                        new MazeSquare("F"),
                        new MazeSquare("G", new MazeSquare("J", joinSquare))
                ),
                new MazeSquare("B",
                        new MazeSquare("D"),
                        joinSquare
                )
        );
        final Path path = new Maze().solve(entrance);
        System.out.println(path.getPath());
    }
}
