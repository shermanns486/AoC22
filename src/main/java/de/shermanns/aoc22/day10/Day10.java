package de.shermanns.aoc22.day10;

import java.util.List;
import java.util.logging.Level;

import de.shermanns.aoc22.Base;

public class Day10 extends Base {
    private static final String INPUT_TXT = "input.txt";

    Integer registerX = 1;

    Integer cycle = 0;

    Integer loesungTeil1 = 0;

    List<Integer> markerList = List.of(20, 60, 100, 140, 180, 220);

    StringBuilder crt = new StringBuilder();

    public static void main(final String[] args) {
        new Day10().loeseRaetselTeil1();
    }

    protected Integer loeseRaetselTeil1() {
        for (final String zeile : this.zeilen) {
            final String[] zeileSplitted = zeile.split(" ");
            if ("addx".equals(zeileSplitted[0])) {
                addX(Integer.parseInt(zeileSplitted[1]));
            }
            else if ("noop".equals(zeileSplitted[0])) {
                noop();
            }
        }

        this.logger.log(Level.INFO, "Lösung Teil 1: {0}", this.loesungTeil1);

        this.logger.log(Level.INFO, "Lösung Teil 2: {0}", printCrt(40));

        return this.loesungTeil1;
    }

    private void noop() {
        this.logger.log(Level.INFO, "noop");

        this.cycle++;
        interrupt();
    }

    private void addX(final int value) {
        this.logger.log(Level.INFO, "addx {0}", value);

        this.cycle++;
        interrupt();

        this.cycle++;
        interrupt();
        this.registerX += value;
    }

    private void interrupt() {
        final int linePos = this.crt.length()
                            % 40;
        if (this.registerX
            - 1 <= linePos
            && this.registerX
               + 1 >= linePos) {
            this.crt.append('#');
        }
        else {
            this.crt.append('.');
        }

        this.logger.log(Level.INFO, "X: {0} Cycle: {1}", new Integer[] { this.registerX, this.cycle });

        if (this.markerList.contains(this.cycle)) {
            final Integer ergebnis = this.cycle
                                     * this.registerX;

            this.logger.log(Level.INFO, "MARKER {0}: {0} * {1} = {2}",
                    new Integer[] { this.cycle, this.registerX, ergebnis });

            this.loesungTeil1 += ergebnis;
        }
    }

    private String printCrt(final int width) {
        final StringBuilder builder = new StringBuilder(System.lineSeparator());
        for (int i = 0; i < 240; i += width) {
            builder.append(this.crt.subSequence(i, i
                                                   + width));
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    @Override
    protected String getInputFile() {
        return Day10.INPUT_TXT;
    }
}
