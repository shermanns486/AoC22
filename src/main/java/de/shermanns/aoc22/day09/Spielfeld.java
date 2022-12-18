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

    private static final int NUMBER_OF_KNOTS = 2;

    private static final Character VOID_CHAR = '.';
    private static final Character START_CHAR = 's';
    private static final Character HEAD_CHAR = 'H';
    private static final Character TAIL_CHAR = 'T';
    private static final Character VISITED_CHAR = '#';

    private final List<Point> knots = new ArrayList<>();
    private final List<Point> visitedByTail = new ArrayList<>();

    public Spielfeld() {
        this.visitedByTail.add(new Point(0, 0));

        for (int i = 0; i <= Spielfeld.NUMBER_OF_KNOTS; i++) {
            this.knots.add(new Point(0, 0));
        }
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
        final Integer headX = this.knots.get(0).getX();
        return headX > max ? headX : max;
    }

    public Integer getMinX() {
        final Integer min = this.visitedByTail.stream() //
                .map(Point::getX) //
                .min(Integer::compareTo) //
                .orElse(0);
        final Integer headX = this.knots.get(0).getX();
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
        final Integer headY = this.knots.get(0).getY();
        return headY > max ? headY : max;
    }

    public Integer getMinY() {
        final Integer min = this.visitedByTail.stream() //
                .map(Point::getY) //
                .min(Integer::compareTo) //
                .orElse(0);
        final Integer headY = this.knots.get(0).getY();
        return headY < min ? headY : min;
    }

    private Point getHead() {
        return this.knots.get(0);
    }

    private Point getTail() {
        return this.knots.get(Spielfeld.NUMBER_OF_KNOTS
                              - 1);
    }

    public void go(final String direction, final int count) {
        for (int i = 0; i < count; i++) {
            switch (direction) {
            case Spielfeld.LEFT:
                getHead().incX(-1);
                break;

            case Spielfeld.RIGHT:
                getHead().incX(1);
                break;

            case Spielfeld.UP:
                getHead().incY(-1);
                break;

            case Spielfeld.DOWN:
                getHead().incY(1);
                break;

            default:
            }

            if (!this.visitedByTail.contains(getTail())) {
                this.visitedByTail.add(new Point(getTail()));
            }

            calculateNewTail();
        }

        // this.logger.log(Level.INFO, "{0}", direction
        // + count
        // + ": "
        // + toString());
    }

    private void calculateNewTail() {
        for (int i = 0; i < Spielfeld.NUMBER_OF_KNOTS
                            - 1; i++) {
            final Point begin = this.knots.get(i);
            final Point end = this.knots.get(i
                                             + 1);

            final int diffX = begin.getX()
                              - end.getX();
            final int diffY = begin.getY()
                              - end.getY();

            if (diffY == 0) {
                if (diffX == -2) {
                    // Left
                    end.incX(-1);
                }

                if (diffX == 2) {
                    // Right
                    end.incX(1);
                }
            }
            else if (diffX == 0) {
                if (diffY == -2) {
                    // Up
                    end.incY(-1);
                }

                if (diffY == 2) {
                    // Down
                    end.incY(1);
                }
            }
            else if (!touching(begin, end)) {
                if (Math.abs(diffX) > 1) {
                    end.incX(diffX
                             / 2);
                }
                else {
                    end.incX(diffX);
                }

                if (Math.abs(diffY) > 1) {
                    end.incY(diffY
                             / 2);
                }
                else {
                    end.incY(diffY);
                }
            }
        }
    }

    protected boolean touching(final Point begin, final Point end) {
        final int diffX = end.getX()
                          - begin.getX();
        final int diffY = end.getY()
                          - begin.getY();

        return diffX == 0
               && diffY == 0
               || Math.abs(diffX) == 1
                  && Math.abs(diffY) < 2;
    }

    @Override
    public String toString() {
        return "Spielfeld [visitedByTail="
               + this.visitedByTail
               + ", knots="
               + this.knots
               + "]";
    }

    public void print() {
        final StringBuilder builder = new StringBuilder();
        builder.append(System.lineSeparator());

        for (int zeile = getMinY(); zeile <= getMaxY(); zeile++) {
            for (int spalte = getMinX(); spalte <= getMaxX(); spalte++) {
                final Point p = new Point(spalte, zeile);

                // Zuerst den ganzen Schwanz
                for (int knot = 0; knot < Spielfeld.NUMBER_OF_KNOTS; knot++) {
                    if (this.knots.get(knot).equals(p)) {
                        builder.append(knot);
                    }
                }

                if (new Point(0, 0).equals(p)) {
                    builder.append(Spielfeld.START_CHAR);
                }
                else if (getHead().equals(p)) {
                    builder.append(Spielfeld.HEAD_CHAR);
                }
                else if (getTail().equals(p)) {
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
                                           + this.visitedByTail.size());
    }
}
