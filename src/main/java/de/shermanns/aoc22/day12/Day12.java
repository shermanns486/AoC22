package de.shermanns.aoc22.day12;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.shermanns.aoc22.Base;

public class Day12 extends Base {
    private static final boolean TEIL_1 = true;
    private static final boolean TEST = false;

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
                char c = this.zeilen.get(y).charAt(x);

                if (c == 'S') {
                    c = 'a';
                    this.startNode = new Node(x, y, c, 0, null);
                    this.startNode.setHeight(c);
                }
                else if (c == 'E') {
                    c = 'z';
                    this.endNode = new Node(x, y, c, 0, null);
                    this.endNode.setHeight(c);
                }

                this.heightMap[y][x] = c;
            }
        }

        this.startNode.setH(h(this.startNode));
    }

    private void loeseRaetsel() {
        System.out.println("Start: "
                           + this.startNode);
        System.out.println("End: "
                           + this.endNode);

        this.openList.add(this.startNode);

        long iteration = 0L;

        while (!this.openList.isEmpty()) {
            this.selectedNode = this.openList.poll();

            System.out.println("\n=== Iteration "
                               + iteration
                               + " ===\n");

            System.out.println(printMap(this.heightMap));

            System.out.println("Selected "
                               + this.selectedNode
                               + "\n");

            if (this.selectedNode.equals(this.endNode)) {
                System.out.println("Endnode found");
                return;
            }

            this.closedSet.add(this.selectedNode);

            final List<Node> expandedNodes = expandNode(this.selectedNode);

            // System.out.println("Expanded Nodes: "
            // + expandedNodes.size()
            // + "\n");

            for (final Node n : expandedNodes) {
                if (!this.closedSet.contains(n)
                    && !this.openList.contains(n)) {
                    this.openList.add(n);
                    // System.out.println("Added "
                    // + n);
                }
            }

            // System.out.println("\nOpen list ("
            // + this.openList.size()
            // + "):\n"
            // + printCollection(this.openList)
            // + "\n");
            // System.out.println("Closed list ("
            // + this.closedSet.size()
            // + "):\n"
            // + printCollection(this.closedSet));

            iteration++;
        }
    }

    private <T> String printCollection(final Collection<T> collection) {
        return collection.stream().map(T::toString).collect(Collectors.joining(",\n", "", ""));
    }

    private boolean closedSetContains(final Point otherP) {
        return this.closedSet.stream().map(Node::getP).anyMatch(p -> p.equals(otherP));
    }

    private List<Node> expandNode(final Node selectedNode) {
        final Point nodeP = selectedNode.getP();
        final int nodeX = nodeP.x();
        final int nodeY = nodeP.y();
        final int nodeG = selectedNode.getG();
        final List<Node> expandedNodes = new ArrayList<>(4);

        final IntPredicate nodeValid = i -> i == 0
                                            || Math.abs(i) == 1;

        final int newG = nodeG
                         + 1;

        // Up
        int newX = nodeX;
        int newY = nodeY
                   - 1;

        if (newX != -1
            && newY != -1
            && newX < this.heightMap[0].length
            && newY < this.heightMap.length) {
            final Node nodeU = new Node(newX, newY, this.heightMap[newY][newX], newG, selectedNode);
            nodeU.setH(h(this.startNode));
            if (nodeValid.test(nodeU.getDeltaHeight())) {
                expandedNodes.add(nodeU);
            }
        }

        // Down
        newY = nodeY
               + 1;

        if (newX != -1
            && newY != -1
            && newX < this.heightMap[0].length
            && newY < this.heightMap.length) {
            final Node nodeD = new Node(newX, newY, this.heightMap[newY][newX], newG, selectedNode);
            nodeD.setH(h(this.startNode));
            if (nodeValid.test(nodeD.getDeltaHeight())) {
                expandedNodes.add(nodeD);
            }
        }

        // Left
        newX = nodeX
               - 1;
        newY = nodeY;

        if (newX != -1
            && newY != -1
            && newX < this.heightMap[0].length
            && newY < this.heightMap.length) {
            final Node nodeL = new Node(newX, newY, this.heightMap[newY][newX], newG, selectedNode);
            nodeL.setH(h(this.startNode));
            if (nodeValid.test(nodeL.getDeltaHeight())) {
                expandedNodes.add(nodeL);
            }
        }

        // Right
        newX = nodeX
               + 1;

        if (newX != -1
            && newY != -1
            && newX < this.heightMap[0].length
            && newY < this.heightMap.length) {
            final Node nodeR = new Node(newX, newY, this.heightMap[newY][newX], newG, selectedNode);
            nodeR.setH(h(this.startNode));
            if (nodeValid.test(nodeR.getDeltaHeight())) {
                expandedNodes.add(nodeR);
            }
        }

        return expandedNodes;
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
            builder.append(String.format("%2d: ", lineIndex));

            for (final int columnIndex : IntStream.range(0, map[0].length).toArray()) {
                final Point p = new Point(columnIndex, lineIndex);

                if (closedSetContains(p)) {
                    builder.append('#');
                }
                else if (this.selectedNode != null
                         && p.equals(this.selectedNode.getP())) {
                    builder.append('!');
                }
                else {
                    builder.append(map[lineIndex][columnIndex]);
                }
            }

            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }
}
