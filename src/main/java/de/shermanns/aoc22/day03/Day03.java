package de.shermanns.aoc22.day03;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;
import java.util.logging.Level;

import de.shermanns.aoc22.Base;
import de.shermanns.aoc22.util.Util;

public class Day03 extends Base {
    private static final String INPUT_TXT = "input.txt";

    public static void main(final String[] args) {
        final Day03 day03 = new Day03();

        day03.logger.info("Lade Datei " + Day03.INPUT_TXT);
        final List<String> zeilen = Util.loadResource(day03.getClass(), Day03.INPUT_TXT, day03.logger);

        day03.loeseRaetselEins(zeilen);
        day03.loeseRaetselZwei(zeilen);
    }

    private void loeseRaetselEins(final List<String> zeilen) {
        int ergebnis = 0;

        for (final String zeile : zeilen) {
            final int zeileLength = zeile.length();
            final String compartment1 = zeile.substring(0, zeileLength / 2);
            final String compartment2 = zeile.substring(zeileLength / 2, zeileLength);

            assert zeile.equals(compartment1 + compartment2);

            final CharacterIterator compartment1Iter = new StringCharacterIterator(compartment1);
            char teilChar = compartment1Iter.current();

            while (teilChar != CharacterIterator.DONE) {
                final String teil = String.valueOf(teilChar);

                if (compartment2.contains(teil)) {
                    ergebnis = ergebnis + bestimmePrio(teilChar);
                    break;
                }

                teilChar = compartment1Iter.next();
            }
        }

        if (this.logger.isLoggable(Level.INFO)) {
            this.logger.info("Ergebnis Rätsel 1: " + ergebnis);
        }
    }

    private void loeseRaetselZwei(final List<String> zeilen) {
        int ergebnis = 0;

        for (int index = 0; index < zeilen.size(); index += 3) {
            final List<Character> zeile1 = convertStringToCharList(zeilen.get(index));
            final List<Character> zeile2 = convertStringToCharList(zeilen.get(index + 1));
            final List<Character> zeile3 = convertStringToCharList(zeilen.get(index + 2));

            for (final Character c : zeile1) {
                if (zeile2.contains(c) && zeile3.contains(c)) {
                    ergebnis = ergebnis + bestimmePrio(c);
                    break;
                }
            }
        }

        if (this.logger.isLoggable(Level.INFO)) {
            this.logger.info("Ergebnis Rätsel 2: " + ergebnis);
        }
    }

    private int bestimmePrio(final Character c) {
        final int buchstabeKleinA = 'a';
        final int buchstabeGrossA = 'A';
        final int buchstabeGrossZ = 'Z';

        if (c <= buchstabeGrossZ) {
            return c - buchstabeGrossA + 27;
        }
        else {
            return c - buchstabeKleinA + 1;
        }
    }

    private List<Character> convertStringToCharList(final String str) {
        return str.chars() //
                .mapToObj(e -> (char) e) //
                .toList();
    }
}
