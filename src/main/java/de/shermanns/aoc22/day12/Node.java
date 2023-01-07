package de.shermanns.aoc22.day12;

import java.util.Objects;

public class Node implements Comparable<Node> {
    private int x;
    private int y;
    private Node parent;
    private int g;
    private int h;
    private char height;
    private Direction direction;

    public Node(final int x, final int y, final char height, final int g, final Node parent) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.parent = parent;
        this.g = g;
        this.h = 0;
        this.direction = null;
    }

    public int getX() {
        return this.x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(final Node parent) {
        this.parent = parent;
    }

    public int getF() {
        return this.g
               - this.h
               + getDeltaHeight();
    }

    public int getG() {
        return this.g;
    }

    public void setG(final int g) {
        this.g = g;
    }

    public int getH() {
        return this.h;
    }

    public void setH(final int h) {
        this.h = h;
    }

    @Override
    public String toString() {
        return String.format("Node [x=%s, y=%s, g=%s, h=%s, height=%s, direction=%s, getF()=%s, getDeltaHeight()=%s]",
                this.x, this.y, this.g, this.h, this.height, this.direction, getF(), getDeltaHeight());
    }

    @Override
    public int compareTo(final Node o) {
        if (getF() == o.getF()) {
            return 0;
        }
        else {
            return getF() < o.getF() ? -1 : 1;
        }
    }

    public char getHeight() {
        return this.height;
    }

    public void setHeight(final char height) {
        this.height = height;
    }

    public int getDeltaHeight() {
        if (this.parent == null) {
            return 0;
        }

        char ourHeight = this.height;
        if (ourHeight == 'S') {
            ourHeight = 'a';
        }
        else if (ourHeight == 'E') {
            ourHeight = 'z';
        }

        char otherHeight = this.parent.getHeight();
        if (otherHeight == 'S') {
            otherHeight = 'a';
        }
        else if (otherHeight == 'E') {
            otherHeight = 'z';
        }

        return ourHeight
               - otherHeight;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(final Direction direction) {
        this.direction = direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Node)) {
            return false;
        }
        final Node other = (Node) obj;
        return this.x == other.x
               && this.y == other.y;
    }
}

enum Direction {
    UP('^'), DOWN('v'), LEFT('<'), RIGHT('>');

    char c;

    Direction(final char c) {
        this.c = c;
    }

    public char getC() {
        return this.c;
    }
}
