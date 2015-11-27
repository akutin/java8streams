package ru.ell.tree;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Alexey Kutin on 9/17/2015.
 */
public class BinaryTree {
    public static int count(Node node, String match) {
        if( node == null) {
            return 0;
        }
        return (match.equals(node.value) ? 1 : 0) + count( node.left, match) + count( node.right, match);
    }

    public static boolean allLeavesSameLevel(Node tree) {
        return getLeavesLevel(tree) >= 0;
    }

    public static int getLeavesLevel(Node node) {
        final boolean noLeft = node.left == null;
        final boolean noRight = node.right == null;

        if( noLeft && noRight) {
            return 0;
        } else if( !noLeft && noRight) {
            return getLeavesLevel( node.left) + 1;
        } else if( noLeft && !noRight) {
            return getLeavesLevel( node.right) + 1;
        } else {
            final int leftLevel = getLeavesLevel(node.left);
            if( leftLevel < 0) {
                return leftLevel;
            }
            if( leftLevel == getLeavesLevel(node.right)) {
                return leftLevel + 1;
            } else {
                return -1;
            }
        }
    }
    public static void main(String[] args) {
        Node tree1 =
            new Node( "12",
                new Node( "5",
                    new Node("3", null, null),
                    null
                ),
                new Node( "7",
                    null,
                    new Node("1", null, null)
                )
            );
        assert getLeavesLevel( tree1) == 2;

        Node tree2 =
            new Node( "12",
                new Node( "5",
                    new Node("3", null, null),
                    null
                ),
                new Node( "7",
                    null,
                    null
                )
            );
        assert getLeavesLevel( tree2) == -1;

        Node tree3 =
                new Node( "12",
                        new Node( "5",
                                new Node("3",
                                        new Node( "1", null ,null),
                                        null
                                ),
                                new Node("9",
                                        new Node("2", null, null),
                                        null
                                )
                        ),
                        null
                );
        assert getLeavesLevel( tree3) == 3;
    }
}
