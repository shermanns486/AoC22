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

        this.logger.log(Level.INFO, "Lösung Teil 1: {0}", loesungTeil1);

        return loesungTeil1;
    }

    private void noop() {
        logger.log(Level.INFO, "noop");
        
        this.cycle++;
        interrupt();
    }

    private void addX(final int value) {
        logger.log(Level.INFO, "addx {0}", value);
        
        this.cycle++;
        interrupt();

        this.cycle++;
        interrupt();
        this.registerX += value;
    }

    private void interrupt() {
        this.logger.log(Level.INFO, "X: {0} Cycle: {1}", new Integer[] { this.registerX, this.cycle });
        
        if (markerList.contains(cycle)) {
            Integer ergebnis = cycle * registerX;
            
            this.logger.log(Level.INFO, "MARKER {0}: {0} * {1} = {2}", new Integer[] { this.cycle, this.registerX, ergebnis });
            
            loesungTeil1 += ergebnis;
        }
    }

    @Override
    protected String getInputFile() {
        return Day10.INPUT_TXT;
    }
}
