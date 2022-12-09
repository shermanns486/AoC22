package de.shermanns.aoc22.day05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.shermanns.aoc22.Base;
import de.shermanns.aoc22.util.Util;

public class Day05 extends Base {
    private static final String INPUT_TXT = "input.txt";

    public static void main(final String[] args) {
        final Day05 day05 = new Day05();

        day05.logger.info("Lade Datei " + Day05.INPUT_TXT);
        final List<String> zeilen = Util.loadResource(day05.getClass(), Day05.INPUT_TXT, day05.logger);

        day05.loeseRaetsel(zeilen);
    }

    private void loeseRaetsel(final List<String> zeilen) {
        final List<int[]> operations = parseOperations(zeilen.subList(10, zeilen.size()));

        List<Stack<Character>> stacks = fillStacks(zeilen.subList(0, 8));
        doWork(operations, stacks);
        final String loesung1 = getLoesung(stacks);

        stacks = fillStacks(zeilen.subList(0, 8));
        doWork9001(operations, stacks);
        final String loesung2 = getLoesung(stacks);

        if (this.logger.isLoggable(Level.INFO)) {
            this.logger.info("Ergebnis Rätsel 1: " + loesung1);
            this.logger.info("Ergebnis Rätsel 2: " + loesung2);
        }
    }

    private void doWork9001(final List<int[]> operations, final List<Stack<Character>> stacks) {
        for (final int[] parameter : operations) {
            final int menge = parameter[0];
            final int vonStack = parameter[1] - 1;
            final int zuStack = parameter[2] - 1;

            final Stack<Character> stackVon = stacks.get(vonStack);
            final Stack<Character> stackZu = stacks.get(zuStack);

            stackZu.addAll(stackVon.subList(stackVon.size() - menge, stackVon.size()));

            for (int i = 0; i < menge; i++) {
                stackVon.pop();
            }
        }
    }

    private String getLoesung(final List<Stack<Character>> stacks) {
        final StringBuilder builder = new StringBuilder();

        for (final Stack<Character> stack : stacks) {
            if (!stack.isEmpty()) {
                builder.append(stack.peek());
            }
        }

        return builder.toString();
    }

    private void doWork(final List<int[]> operations, final List<Stack<Character>> stacks) {
        for (final int[] parameter : operations) {
            final int menge = parameter[0];
            final int vonStack = parameter[1] - 1;
            final int zuStack = parameter[2] - 1;

            for (int i = 0; i < menge; i++) {
                stacks.get(zuStack)
                        .push(stacks.get(vonStack)
                                .pop());
            }
        }
    }

    private List<int[]> parseOperations(final List<String> zeilen) {
        final List<int[]> operations = new LinkedList<>();

        final Pattern regExPattern = Pattern.compile("(\\d+)");

        for (final String zeile : zeilen) {
            final Matcher matcher = regExPattern.matcher(zeile);

            final int[] operation = new int[3];
            int index = 0;

            while (matcher.find()) {
                operation[index] = Integer.parseInt(matcher.group());
                index++;
            }

            operations.add(operation);
        }

        return operations;
    }

    private List<Stack<Character>> fillStacks(final List<String> zeilen) {
        final List<Stack<Character>> stacks = initStacks();

        final List<String> stackRows = new ArrayList<>(zeilen);
        Collections.reverse(stackRows);

        for (final String stackRow : stackRows) {
            int index = 1;

            for (final Stack<Character> stack : stacks) {
                final Character c = stackRow.charAt(index);
                if (c != ' ') {
                    stack.push(c);
                }
                index += 4;
            }
        }

        return stacks;
    }

    private List<Stack<Character>> initStacks() {
        final List<Stack<Character>> stacks = new LinkedList<>();
        for (int i = 0; i < 9; i++) {
            stacks.add(new Stack<>());
        }
        return stacks;
    }
}
