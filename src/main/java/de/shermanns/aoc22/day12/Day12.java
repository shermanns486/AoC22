package de.shermanns.aoc22.day12;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.shermanns.aoc22.Base;

public class Day12 extends Base {
    private static final boolean TEIL_1 = true;
    private static final boolean TEST = true;

    private static final String INPUT_TXT = "input.txt";
    private static final String TEST_TXT = "test.txt";

    private char[][] heightMap;

    private final PriorityQueue<Node> openList = new PriorityQueue<>();

    private final Set<Node> closedSet = new HashSet<>();

    private Node startNode;
    private Node endNode;
    private Node selectedNode;

    public Day12() {
        loadData();
    }

    @Override
    protected String getInputFile() {
        if (Day12.TEST) {
            return Day12.TEST_TXT;
        }
        else {
            return Day12.INPUT_TXT;
        }
    }

    public static void main(final String[] args) {
        final Day12 day12 = new Day12();

        System.out.println(day12.printMap(day12.heightMap));

        day12.loeseRaetsel();
    }

    private void loadData() {
        final int maxX = this.zeilen.get(0).length();
        final int maxY = this.zeilen.size();
        this.heightMap = new char[maxY][maxX];

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                final char character = this.zeilen.get(y).charAt(x);

                if (character == 'S') {
                    this.startNode = new Node(x, y, 'a', 0, null);
                }
                else if (character == 'E') {
                    this.endNode = new Node(x, y, 'z', 0, null);
                }

                this.heightMap[y][x] = character;
            }
        }

        this.startNode.setH(h(this.startNode));
    }

    private void loeseRaetsel() {
        System.out.println("Start: "
                           + this.startNode);
        System.out.println("End: "
                           + this.endNode);
        System.out.println();

        this.openList.add(this.startNode);

        long iteration = 0L;
        boolean endNodeFound = false;

        while (!this.openList.isEmpty()) {
            this.selectedNode = this.openList.poll();
            if (this.selectedNode.getParent() != null) {
                this.selectedNode.getParent().setDirection(this.selectedNode.getDirection());
            }

            System.out.println("=== Iteration "
                               + iteration
                               + " ===\n");

            System.out.println(printMap(this.heightMap));

            if (Day12.TEST) {
                System.out.println("Selected "
                                   + this.selectedNode
                                   + "\n");
            }

            if (this.selectedNode.equals(this.endNode)) {
                System.out.println("SUCCESS: Endnode found after "
                                   + iteration
                                   + " iteration(s)");

                endNodeFound = true;
                break;
            }

            this.closedSet.add(this.selectedNode);

            final List<Node> expandedNodes = expandNode(this.selectedNode);
            for (final Node n : expandedNodes) {
                if (getNode(this.closedSet, n.getP()).isEmpty()
                    && getNode(this.openList, n.getP()).isEmpty()) {
                    this.openList.add(n);

                    if (Day12.TEST) {
                        System.out.println("Added "
                                           + n);
                    }
                }
            }
            if (Day12.TEST) {
                System.out.println();
            }

            if (Day12.TEST) {
                System.out.println("\n== Open list ("
                                   + this.openList.size()
                                   + ") ==\n\n"
                                   + printCollection(this.openList)
                                   + "\n"
                                   + "\n== Closed list ("
                                   + this.closedSet.size()
                                   + ") ==\n\n"
                                   + printCollection(this.closedSet)
                                   + "\n");
            }

            iteration++;
        }

        if (!endNodeFound) {
            System.out.println("ERROR: Endnode not found after "
                               + iteration
                               + " iterations");
        }
    }

    private <T> String printCollection(final Collection<T> collection) {
        return collection.stream() //
                .map(T::toString) //
                .collect(Collectors.joining(",\n", "", ""));
    }

    private Optional<Node> getNode(final Collection<Node> col, final Point otherP) {
        return col.stream() //
                .filter(n -> n.getP().equals(otherP)) //
                .findFirst();
    }

    private List<Node> expandNode(final Node selectedNode) {
        final Point nodeP = selectedNode.getP();
        final int nodeX = nodeP.x();
        final int nodeY = nodeP.y();
        final int nodeG = selectedNode.getG();
        final int newG = nodeG
                         + 1;
        final List<Node> expandedNodes = new ArrayList<>(4);
        final IntPredicate nodeValid = i -> i == 0
                                            || Math.abs(i) == 1;

        // Up
        expandNode(selectedNode, expandedNodes, nodeX, nodeY
                                                       - 1,
                newG, Direction.UP, nodeValid);

        // Down
        expandNode(selectedNode, expandedNodes, nodeX, nodeY
                                                       + 1,
                newG, Direction.DOWN, nodeValid);

        // Left
        expandNode(selectedNode, expandedNodes, nodeX
                                                - 1,
                nodeY, newG, Direction.LEFT, nodeValid);

        // Right
        expandNode(selectedNode, expandedNodes, nodeX
                                                + 1,
                nodeY, newG, Direction.RIGHT, nodeValid);

        return expandedNodes;
    }

    public void expandNode(final Node selectedNode, final List<Node> expandedNodes, final int newX, final int newY,
            final int newG, final Direction direction, final IntPredicate nodeValid) {
        if (newX != -1
            && newY != -1
            && newX < this.heightMap[0].length
            && newY < this.heightMap.length) {

            final Node node = new Node(newX, newY, this.heightMap[newY][newX], newG, selectedNode);
            node.setDirection(direction);
            node.setH(h(node));

            if (nodeValid.test(node.getDeltaHeight())) {
                expandedNodes.add(node);
            }
        }
    }

    private int h(final Node n) {
        return Math.abs(n.getP().x()
                        - this.endNode.getP().x())
               + Math.abs(n.getP().y()
                          - this.endNode.getP().y());
    }

    private String printMap(final char[][] map) {
        final StringBuilder builder = new StringBuilder();

        for (final int lineIndex : IntStream.range(0, map.length).toArray()) {
            // Print linenumber
            builder.append(String.format("%2d: ", lineIndex));

            for (final int columnIndex : IntStream.range(0, map[0].length).toArray()) {
                final Point actualP = new Point(columnIndex, lineIndex);

                final Optional<Node> closedNode = getNode(this.closedSet, actualP);
                if (closedNode.isPresent()) {
                    builder.append(closedNode.get().getDirection().getC());
                }
                else if (this.selectedNode != null
                         && actualP.equals(this.selectedNode.getP())) {
                    builder.append('!');
                }
                else {
                    builder.append(map[lineIndex][columnIndex]);
                    // builder.append('.');
                }
            }

            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }
}
