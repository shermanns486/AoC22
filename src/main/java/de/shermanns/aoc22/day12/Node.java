package de.shermanns.aoc22.day12;

import java.util.Objects;

public class Node implements Comparable<Node> {
    private Point p;
    private Node parent;
    private int g;
    private int h;
    private char height;
    private Direction direction;

    public Node(final int x, final int y, final char height, final int g, final Node parent) {
        this.p = new Point(x, y);
        this.height = height;
        this.parent = parent;
        this.g = g;
        this.h = 0;
        this.direction = null;
    }

    public Point getP() {
        return this.p;
    }

    public void setP(final Point p) {
        this.p = p;
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
        return String.format("Node [p=%s, f=%s, g=%s, h=%s, height=%s, deltaHeight=%s, direction=%s]", this.p, getF(),
                this.g, this.h, this.height, getDeltaHeight(), getDirection());
    }

    @Override
    public int compareTo(final Node o) {
        if (getF() == o.getF()) {
            return 0;
        }
        else {
            return getF() < o.getF() ? 1 : -1;
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

    @Override
    public int hashCode() {
        return Objects.hash(this.p);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null
            || getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        return Objects.equals(this.p, other.p);
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(final Direction direction) {
        this.direction = direction;
    }
}

record Point(int x, int y) {
    @Override
    public String toString() {
        return "[x="
               + this.x
               + ", y="
               + this.y
               + "]";
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
