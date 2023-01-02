package de.shermanns.aoc22.day12;

import java.util.Objects;

public class Node implements Comparable<Node> {
    private Point p;
    private Node parent;
    private int g;
    private int h;
    private char height;

    public Node(final int x, final int y, final char height, final int g, final Node parent) {
        this.p = new Point(x, y);
        this.height = height;
        this.parent = parent;
        this.g = g;
        this.h = 0;
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
        return this.g + this.h + getDeltaHeight();
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
        return String.format("Node [%n  p=%s,%n  f=%s,%n  g=%s,%n  h=%s,%n  height=%s,%n  deltaHeight=%s%n]", this.p,
                getF(), this.g, this.h, this.height, getDeltaHeight());
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
        return this.height - this.parent.getHeight();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.g, this.h, this.height, this.p, this.parent);
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
        return this.p.equals(other.p);
    }
}

record Point(int x, int y) {
    @Override
    public String toString() {
        return "[x=" + this.x + ", y=" + this.y + "]";
    }
}
