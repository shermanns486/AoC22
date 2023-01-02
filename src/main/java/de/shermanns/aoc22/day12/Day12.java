package de.shermanns.aoc22.day12;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Predicate;
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
        final int maxX = this.zeilen.get(0)
                .length();
        final int maxY = this.zeilen.size();
        this.heightMap = new char[maxY + 2][maxX + 2];

        for (int i = 0; i < maxX + 2; i++) {
            this.heightMap[0][i] = '~';
        }

        for (int y = 0; y < maxY; y++) {
            this.heightMap[y + 1][0] = '~';

            for (int x = 0; x < maxX; x++) {
                final char c = this.zeilen.get(y)
                        .charAt(x);

                if (c == 'S') {
                    this.startNode = new Node(x + 1, y + 1, 'a', 0, null);
                }
                else if (c == 'E') {
                    this.endNode = new Node(x + 1, y + 1, 'z', 0, null);
                    this.endNode.setHeight('z');
                }

                this.heightMap[y + 1][x + 1] = c;
            }

            this.heightMap[y + 1][this.heightMap[0].length - 1] = '~';
        }

        for (int i = 0; i < maxX + 2; i++) {
            this.heightMap[maxY + 1][i] = '~';
        }

        this.startNode.setH(h(this.startNode));
    }

    private void loeseRaetsel() {
        System.out.println("Start: " + this.startNode);
        System.out.println("End: " + this.endNode);

        this.openList.add(this.startNode);

        long iteration = 0L;

        while (!this.openList.isEmpty()) {
            iteration++;
            final Node selectedNode = this.openList.poll();

            System.out.println("\n=== Iteration " + iteration + " ===\n");
            
            System.out.println(printMap(heightMap));
            
            System.out.println("Inspecting " + selectedNode + "\n");

            if (selectedNode.equals(this.endNode)) {
                System.out.println("Endnode found");
                return;
            }

            this.closedSet.add(selectedNode);

            final List<Node> expandedNodes = expandNode(selectedNode);

            System.out.println(expandedNodes.size() + " expanded Nodes" + "\n");

            for (final Node n : expandedNodes) {
                if (!this.closedSet.contains(n) && !this.openList.contains(n)) {
                    this.openList.add(n);
                    System.out.println("Node added: " + n + "\n");
                }
            }

            System.out.println("Open list: " + this.openList + "\n");
            System.out.println("Closed list: " + this.closedSet);

            if (iteration == 100L) {
                return;
            }
        }
    }

    private boolean closedSetContains(final int x, final int y) {
        return this.closedSet.contains(new Node(x, y, ' ', 0, null));
    }

    private List<Node> expandNode(final Node selectedNode) {
        final Point nodeP = selectedNode.getP();
        final int nodeX = nodeP.x();
        final int nodeY = nodeP.y();
        final int nodeG = selectedNode.getG();
        final List<Node> expandedNodes = new ArrayList<>(4);

        final Predicate<Integer> nodeValid = i -> i == 0 || Math.abs(i) == 1;

        // Up
        final Node nodeU = new Node(nodeX, nodeY - 1, this.heightMap[nodeX][nodeY - 1], nodeG + 1, selectedNode);
        nodeU.setH(h(this.startNode));
        if (nodeValid.test(nodeU.getDeltaHeight())) {
            expandedNodes.add(nodeU);
        }
        // Down
        final Node nodeD = new Node(nodeX, nodeY + 1, this.heightMap[nodeX][nodeY + 1], nodeG + 1, selectedNode);
        nodeD.setH(h(this.startNode));
        if (nodeValid.test(nodeD.getDeltaHeight())) {
            expandedNodes.add(nodeD);
        }
        // Left
        final Node nodeL = new Node(nodeX - 1, nodeY, this.heightMap[nodeX - 1][nodeY], nodeG + 1, selectedNode);
        nodeL.setH(h(this.startNode));
        if (nodeValid.test(nodeL.getDeltaHeight())) {
            expandedNodes.add(nodeL);
        }
        // Right
        final Node nodeR = new Node(nodeX + 1, nodeY, this.heightMap[nodeX + 1][nodeY], nodeG + 1, selectedNode);
        nodeR.setH(h(this.startNode));
        if (nodeValid.test(nodeR.getDeltaHeight())) {
            expandedNodes.add(nodeR);
        }

        return expandedNodes;
    }

    private int h(final Node n) {
        return Math.abs(n.getP()
                .x()
                - this.endNode.getP()
                        .x())
                + Math.abs(n.getP()
                        .y()
                        - this.endNode.getP()
                                .y());
    }

    private String printMap(final char[][] map) {
        final StringBuilder builder = new StringBuilder();

        for (final int lineIndex : IntStream.range(0, map.length)
                .toArray()) {
            builder.append(String.format("%2d: ", lineIndex));

            for (final int columnIndex : IntStream.range(0, map[0].length)
                    .toArray()) {
                if (closedSetContains(columnIndex, lineIndex)) {
                    builder.append('#');
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
