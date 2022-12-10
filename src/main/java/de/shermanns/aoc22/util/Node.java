package de.shermanns.aoc22.util;

import java.util.ArrayList;
import java.util.List;

/**
 * https://www.javagists.com/java-tree-data-structure
 * 
 * @author w w w. j a v a g i s t s . c o m
 *
 */
public class Node<T> {

    private T data = null;

    private final List<Node<T>> children = new ArrayList<>();

    private Node<T> parent = null;

    public Node(final T data) {
        this.data = data;
    }

    public Node<T> addChild(final Node<T> child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public Node<T> addChild(final T data) {
        final Node<T> child = new Node<>(data);
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public void addChildren(final List<Node<T>> children) {
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    public List<Node<T>> getChildren() {
        return this.children;
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    @Override
    public String toString() {
        return "Node [data=" + this.data + "]";
    }

    public T getData() {
        return this.data;
    }

    public void setData(final T data) {
        this.data = data;
    }

    private void setParent(final Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getParent() {
        return this.parent;
    }

    // additional functions
    public Node<T> getRoot() {
        if (isRoot()) {
            return this;
        }
        return this.parent.getRoot();
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public int getHeight() {
        Node<T> localNode = this;

        int i = 0;
        while (!localNode.isRoot()) {
            localNode = localNode.getParent();
            i++;
        }

        return i;
    }

    public void deleteNode() {
        if (this.parent != null) {
            final int index = this.parent.getChildren()
                    .indexOf(this);
            this.parent.getChildren()
                    .remove(this);
            for (final Node<T> each : getChildren()) {
                each.setParent(this.parent);
            }
            this.parent.getChildren()
                    .addAll(index, this.getChildren());
        }
        else {
            deleteRootNode();
        }
        this.getChildren()
                .clear();
    }

    public Node<T> deleteRootNode() {
        if (this.parent != null) {
            throw new IllegalStateException("deleteRootNode not called on root");
        }
        Node<T> newParent = null;
        if (!getChildren().isEmpty()) {
            newParent = getChildren().get(0);
            newParent.setParent(null);
            getChildren().remove(0);
            for (final Node<T> each : getChildren()) {
                each.setParent(newParent);
            }
            newParent.getChildren()
                    .addAll(getChildren());
        }
        this.getChildren()
                .clear();
        return newParent;
    }

    // deletes all the leaf nodes under this node
    public void deleteAllLeafNodes() {
        final List<Node<T>> tobeDeletedChildren = new ArrayList<>();
        deleteAllLeafNodes(this, tobeDeletedChildren);
        tobeDeletedChildren.forEach(Node::deleteNode);
    }

    private void deleteAllLeafNodes(final Node<T> node, final List<Node<T>> tobeDeletedChildren) {
        node.getChildren()
                .forEach(each -> {
                    if (each.getChildren()
                            .isEmpty()) {
                        tobeDeletedChildren.add(each);
                        return;
                    }
                    deleteAllLeafNodes(each, tobeDeletedChildren);
                });
    }
}
