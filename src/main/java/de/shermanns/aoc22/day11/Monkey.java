package de.shermanns.aoc22.day11;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Monkey {
    Logger logger;

    Integer number;
    List<Long> items = new ArrayList<>();
    Function<Long, Long> operation;
    Predicate<Long> test;
    Integer toMonkeyNrTrue;
    Integer toMonkeyNrFalse;
    Integer inceptionCounter = 0;
    Integer divisor;

    public Monkey(final Integer number, final Logger logger) {
        this.number = number;
        this.logger = logger;
    }

    public void addThrowToNrFalse(final String throwToString) {
        final String[] thr = throwToString.split(" ");
        this.toMonkeyNrFalse = Integer.parseInt(thr[thr.length - 1]);

        System.out.println("|M" + this.number + "| FALSE --> M" + this.toMonkeyNrFalse + " |");
    }

    public void addThrowToNrTrue(final String throwToString) {
        final String[] thr = throwToString.split(" ");
        this.toMonkeyNrTrue = Integer.parseInt(thr[thr.length - 1]);

        System.out.println("|M" + this.number + "| TRUE  --> M" + this.toMonkeyNrTrue + " |");
    }

    public void addItems(final String itemString) {
        final String[] newItems = itemString.split(",");
        this.items.addAll(Stream.of(newItems)
                .map(String::trim)
                .map(Long::parseLong)
                .toList());

        System.out.println("|M" + this.number + "| " + this.items.toString());
    }

    public void addOperation(final String operationString) {
        final StringBuilder messageBuilder = new StringBuilder() //
                .append("|M") //
                .append(this.number) //
                .append("| new = old");
        final String[] op = operationString.substring(operationString.indexOf("old"))
                .split(" ");

        if ("*".equals(op[1]) && "old".equals(op[2])) {
            messageBuilder.append(" * old");

            this.operation = old -> old * old;
        }
        else if ("*".equals(op[1]) && op[2].matches("\\d*")) {
            messageBuilder.append(" * ") //
                    .append(Integer.parseInt(op[2]));

            this.operation = old -> old * Integer.parseInt(op[2]);
        }
        else if ("+".equals(op[1]) && op[2].matches("\\d*")) {
            messageBuilder.append(" + ") //
                    .append(Integer.parseInt(op[2]));

            this.operation = old -> old + Integer.parseInt(op[2]);
        }
        else {
            messageBuilder.append("???");
        }
        messageBuilder.append(" |");

        System.out.println(messageBuilder);
    }

    public void addTest(final String testString) {
        final String[] tst = testString.split(" ");
        this.divisor = Integer.parseInt(tst[tst.length - 1]);
        this.test = i -> i % this.divisor == 0;

        System.out.println("|M" + this.number + "| If i % " + this.divisor + " |");
    }

    @Override
    public String toString() {
        return String.format("|M%d| %4d | %s |", this.number, this.inceptionCounter, this.items);
    }

    public Integer getInceptionCounter() {
        return this.inceptionCounter;
    }

    public Integer getDivisor() {
        return this.divisor;
    }
}
