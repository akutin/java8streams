package ru.ell.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Restore a tree
 *
 * Based on http://www.math.ucla.edu/~wittman/10b.1.10w/Lectures/Lec18.pdf
 */
public class RestoreTree {
    /**
     * Restores a tree from preOrder and inOrder
     *
     * {@code preOrder} is modified for each level
     */
    public Node restore(List<String> preOrder, List<String> inOrder) {
        if( inOrder.isEmpty()) {
            return null;
        }
        final String root = preOrder.remove(0);

        List<String> left = inOrder.indexOf(root) < 0 ? Collections.emptyList() : inOrder.subList(0, inOrder.indexOf(root));
        List<String> right = inOrder.indexOf(root) < 0 ? Collections.emptyList() : inOrder.subList(inOrder.indexOf(root) + 1, inOrder.size());

        return new Node(root, restore(preOrder, left), restore(preOrder, right));
    }

    public static void main(String[] args) {
        /**
                    15
                5            16
            3       12           20
                10      13   18      23
            6
                7
         */
        final List<String> inOrder = Arrays.asList("3", "5", "6", "7", "10", "12", "13", "15", "16", "18", "20", "23");
        final List<String> preOrder = Arrays.asList("15", "5", "3", "12", "10", "6", "7", "13", "16", "20", "18", "23");

        final Node tree = new RestoreTree().restore(new ArrayList<>(preOrder), inOrder);
        tree.getChildren();
    }
}
