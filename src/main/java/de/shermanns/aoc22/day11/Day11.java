package de.shermanns.aoc22.day11;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import de.shermanns.aoc22.Base;

public class Day11 extends Base {
    public static final boolean TEIL_1 = false;
    private static final boolean TEST = false;

    private static final String INPUT_TXT = "input.txt";
    private static final String TEST_TXT = "test.txt";

    private static final int ANZAHL_RUNDEN_TEIL_1 = 20;
    private static final int ANZAHL_RUNDEN_TEIL_2 = 10000;

    List<Monkey> monkeyList = new ArrayList<>();
    long supermod = 0L;

    @Override
    protected String getInputFile() {
        if (Day11.TEST) {
            return Day11.TEST_TXT;
        }
        else {
            return Day11.INPUT_TXT;
        }
    }

    public static void main(final String[] args) {
        new Day11().loeseRaetselTeil();
    }

    private void loeseRaetselTeil() {
        ladeDaten();
        System.out.println(printMonkeys());

        this.supermod = berechneSupermod();

        final int runden;
        if (Day11.TEIL_1) {
            runden = Day11.ANZAHL_RUNDEN_TEIL_1;
        }
        else {
            runden = Day11.ANZAHL_RUNDEN_TEIL_2;
        }

        for (int i = 0; i < runden; i++) {
            spiele(i);
        }

        System.out.println("Lösung Rätsel: " + getInceptions());
    }

    private long berechneSupermod() {
        return this.monkeyList.stream()
                .map(Monkey::getDivisor)
                .reduce(1, (a, b) -> a * b);
    }

    private long getInceptions() {
        final List<Integer> inceptionCounterList = this.monkeyList.stream()
                .map(Monkey::getInceptionCounter)
                .collect(Collectors.toList());

        inceptionCounterList.sort(Integer::compareTo);
        Collections.reverse(inceptionCounterList);

        return (long) inceptionCounterList.get(0) * inceptionCounterList.get(1);
    }

    private void spiele(final Integer runde) {
        for (final Monkey monkey : this.monkeyList) {
            processMonkey(runde, monkey);
        }

        System.out.println("\n=== Ergebnis Runde " + (runde + 1) + " ===\n" + printMonkeys());
    }

    private void processMonkey(final Integer runde, final Monkey monkey) {
        while (!monkey.items.isEmpty()) {
            monkey.inceptionCounter++;

            final Long itemWorryLevel = monkey.items.remove(0);
            Long itemWorryLevelOp = monkey.operation.apply(itemWorryLevel);
            if (Day11.TEIL_1) {
                itemWorryLevelOp = BigDecimal.valueOf(itemWorryLevelOp)
                        .divide(BigDecimal.valueOf(3), RoundingMode.DOWN)
                        .longValue();
            }
            else {
                itemWorryLevelOp %= this.supermod;
            }

            Integer toMonkeyNr;
            if (monkey.test.test(itemWorryLevelOp)) {
                toMonkeyNr = monkey.toMonkeyNrTrue;
            }
            else {
                toMonkeyNr = monkey.toMonkeyNrFalse;
            }

            final List<Long> toMonkeyItems = this.monkeyList.get(toMonkeyNr).items;
            toMonkeyItems.add(itemWorryLevelOp);

            System.out.printf("|R%1$2d|M%2$d| %3$4d -> %4$4d --> M%5$d%n", (runde + 1), monkey.number, itemWorryLevel,
                    itemWorryLevelOp, toMonkeyNr);
        }
    }

    private String printMonkeys() {
        final StringBuilder messageBuilder = new StringBuilder() //
                .append(System.lineSeparator());

        for (final Monkey monkey : this.monkeyList) {
            messageBuilder.append(monkey) //
                    .append(System.lineSeparator());
        }

        return messageBuilder.toString();
    }

    private void ladeDaten() {
        int index = -1;
        for (final String zeile : this.zeilen) {
            if (zeile.contains("Monkey")) {
                index++;
                this.monkeyList.add(new Monkey(index, this.logger));

                System.out.println();
            }
            else {
                final String[] zeileSplitted = zeile.split(":");

                if (zeile.contains("Starting items")) {
                    this.monkeyList.get(index)
                            .addItems(zeileSplitted[1]);
                }
                else if (zeile.contains("Operation")) {
                    this.monkeyList.get(index)
                            .addOperation(zeileSplitted[1]);
                }
                else if (zeile.contains("Test")) {
                    this.monkeyList.get(index)
                            .addTest(zeileSplitted[1]);
                }
                else if (zeile.contains("If true")) {
                    this.monkeyList.get(index)
                            .addThrowToNrTrue(zeileSplitted[1]);
                }
                else if (zeile.contains("If false")) {
                    this.monkeyList.get(index)
                            .addThrowToNrFalse(zeileSplitted[1]);
                }
            }
        }
    }
}
