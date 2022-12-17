package de.shermanns.aoc22.day09;

import de.shermanns.aoc22.Base;

public class Day09 extends Base {
    private static final String INPUT_TXT = "input.txt";

    public static void main(final String[] args) {
        new Day09().loeseRaetsel();
    }

    private void loeseRaetsel() {
        final Spielfeld spielfeld = new Spielfeld();
        
        for (String zeile : zeilen) {
            String[] zeileSplitted = zeile.split(" ");
            
            spielfeld.go(zeileSplitted[0], Integer.parseInt(zeileSplitted[1]));
        }
        
        spielfeld.print();
    }

    @Override
    protected String getInputFile() {
        return Day09.INPUT_TXT;
    }
}
