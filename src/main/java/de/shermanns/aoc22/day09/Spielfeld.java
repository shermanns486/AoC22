package de.shermanns.aoc22.day09;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

class Point {
    private Integer x;
    private Integer y;

    Point(final Integer x, final Integer y) {
        this.x = x;
        this.y = y;
    }

    Point(final Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    public void incX(final Integer count) {
        this.x += count;
    }

    public void incY(final Integer count) {
        this.y += count;
    }

    @Override
    public String toString() {
        return "( "
               + this.x
               + ", "
               + this.y
               + " )";
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
        if (obj == null
            || getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        return Objects.equals(this.x, other.x)
               && Objects.equals(this.y, other.y);
    }
}

public class Spielfeld {
    public static final String UP = "U";
    public static final String DOWN = "D";
    public static final String LEFT = "L";
    public static final String RIGHT = "R";

    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final Character VOID_CHAR = '.';
    private static final Character START_CHAR = 's';
    private static final Character HEAD_CHAR = 'H';
    private static final Character TAIL_CHAR = 'T';
    private static final Character VISITED_CHAR = '#';

    private final List<Point> visitedByTail = new ArrayList<>();

    private final Point start = new Point(0, 0);
    private final Point head = new Point(0, 0);
    private final Point tail = new Point(0, 0);

    public Spielfeld() {
        this.visitedByTail.add(new Point(0, 0));
    }

    public int getWidth() {
        return Math.abs(getMinX())
               + Math.abs(getMaxX());
    }

    public Integer getMaxX() {
        final Integer max = this.visitedByTail.stream() //
                .map(Point::getX) //
                .max(Integer::compareTo) //
                .orElse(0);
        final Integer headX = this.head.getX();
        return headX > max ? headX : max;
    }

    public Integer getMinX() {
        final Integer min = this.visitedByTail.stream() //
                .map(Point::getX) //
                .min(Integer::compareTo) //
                .orElse(0);
        final Integer headX = this.head.getX();
        return headX < min ? headX : min;
    }

    public int getHeight() {
        return Math.abs(getMinY())
               + Math.abs(getMaxY());
    }

    public Integer getMaxY() {
        final Integer max = this.visitedByTail.stream() //
                .map(Point::getY) //
                .max(Integer::compareTo) //
                .orElse(0);
        final Integer headY = this.head.getY();
        return headY > max ? headY : max;
    }

    public Integer getMinY() {
        final Integer min = this.visitedByTail.stream() //
                .map(Point::getY) //
                .min(Integer::compareTo) //
                .orElse(0);
        final Integer headY = this.head.getY();
        return headY < min ? headY : min;
    }

    public void go(final String direction, final int count) {
        for (int i = 0; i < count; i++) {
            switch (direction) {
            case Spielfeld.LEFT:
                this.head.incX(-1);
                break;

            case Spielfeld.RIGHT:
                this.head.incX(1);
                break;

            case Spielfeld.UP:
                this.head.incY(-1);
                break;

            case Spielfeld.DOWN:
                this.head.incY(1);
                break;

            default:
            }

            if (!this.visitedByTail.contains(this.tail)) {
                this.visitedByTail.add(new Point(this.tail));
            }

            calculateNewTail();

            // print();
        }

        // this.logger.log(Level.INFO, "{0}", direction
        // + count
        // + ": "
        // + toString());
    }

    private void calculateNewTail() {
        final int diffX = this.head.getX()
                          - this.tail.getX();
        final int diffY = this.head.getY()
                          - this.tail.getY();

        if (diffY == 0) {
            if (diffX == -2) {
                // Left
                this.tail.incX(-1);
            }

            if (diffX == 2) {
                // Right
                this.tail.incX(1);
            }
        }
        else if (diffX == 0) {
            if (diffY == -2) {
                // Up
                this.tail.incY(-1);
            }

            if (diffY == 2) {
                // Down
                this.tail.incY(1);
            }
        }
        else if (!touching(this.head, this.tail)) {
            if (Math.abs(diffX) > 1) {
                this.tail.incX(diffX
                               / 2);
            }
            else {
                this.tail.incX(diffX);
            }

            if (Math.abs(diffY) > 1) {
                this.tail.incY(diffY
                               / 2);
            }
            else {
                this.tail.incY(diffY);
            }
        }
    }

    protected boolean touching(final Point head, final Point tail) {
        final int tailHeadDiffX = tail.getX()
                                  - head.getX();
        final int tailHeadDiffY = tail.getY()
                                  - head.getY();

        return tailHeadDiffX == 0
               && tailHeadDiffY == 0
               || Math.abs(tailHeadDiffX) == 1
                  && Math.abs(tailHeadDiffY) < 2;
    }

    @Override
    public String toString() {
        return "Spielfeld [visitedByTail="
               + this.visitedByTail
               + ", head="
               + this.head
               + ", tail="
               + this.tail
               + "]";
    }

    public void print() {
        final StringBuilder builder = new StringBuilder();
        builder.append(System.lineSeparator());

        for (int zeile = getMinY(); zeile <= getMaxY(); zeile++) {
            for (int spalte = getMinX(); spalte <= getMaxX(); spalte++) {
                final Point p = new Point(spalte, zeile);

                if (this.start.equals(p)) {
                    builder.append(Spielfeld.START_CHAR);
                }
                else if (this.head.equals(p)) {
                    builder.append(Spielfeld.HEAD_CHAR);
                }
                else if (this.tail.equals(p)) {
                    builder.append(Spielfeld.TAIL_CHAR);
                }
                else if (this.visitedByTail.contains(p)) {
                    builder.append(Spielfeld.VISITED_CHAR);
                }
                else {
                    builder.append(Spielfeld.VOID_CHAR);
                }
            }
            builder.append(System.lineSeparator());
        }

        this.logger.log(Level.INFO, builder::toString);
        this.logger.log(Level.INFO, "{0}", "Ergebnis Rätsel 1: "
                                           + (this.visitedByTail.size()
                                              + 1));
    }
}
