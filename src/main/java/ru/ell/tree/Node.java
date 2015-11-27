package ru.ell.tree;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by Alexey Kutin on 9/17/2015.
 */
public class Node {
    public final String value;
    public final Node left;
    public final Node right;

    public Node(String value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public Collection<Node> getChildren() {
        final Collection<Node> children = new LinkedList<>();
        if( this.left != null) {
            children.add( this.left);
        }
        if( this.right != null) {
            children.add( this.right);
        }

        return children;
    }

    @Override
    public String toString() {
        return value;
    }
}
