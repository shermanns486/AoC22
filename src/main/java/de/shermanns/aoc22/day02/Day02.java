package de.shermanns.aoc22.day02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import de.shermanns.aoc22.Base;

public class Day02 extends Base {
    private static final String INPUT_TXT = "input.txt";

    private static final Map<String, Integer> punkteMap = new HashMap<>();
    static {
        Day02.punkteMap.put("X", 1); // Stein
        Day02.punkteMap.put("Y", 2); // Papier
        Day02.punkteMap.put("Z", 3); // Schere
    }

    // Stein schlägt Schere, Schere schlägt Papier, Papier schlägt Stein
    private static final List<String> verlorenList = List.of("AZ", "CY", "BX");

    private static final List<String> unentschiedenList = List.of("AX", "BY", "CZ");

    private static final List<String> gewonnenList = List.of("AY", "BZ", "CX");

    private void raetselLoesen() {
        int ergebnisRaetselEins = 0;
        int ergebnisRaetselZwei = 0;

        for (final String zeile : this.zeilen) {
            final String[] tokens = zeile.split(" ");

            ergebnisRaetselEins = ergebnisRaetselEins
                                  + bewerteVersuchRaetselEins(tokens);
            ergebnisRaetselZwei = ergebnisRaetselZwei
                                  + bewerteVersuchRaetselZwei(tokens);
        }

        if (this.logger.isLoggable(Level.INFO)) {
            this.logger.info("Ergebnis Rätsel 1: "
                             + ergebnisRaetselEins);
            this.logger.info("Ergebnis Rätsel 2: "
                             + ergebnisRaetselZwei);
        }
    }

    private int bewerteVersuchRaetselEins(final String[] tokens) {
        int ergebnis = 0;

        if (Day02.verlorenList.contains(tokens[0]
                                        + tokens[1])) {
            ergebnis = ergebnis
                       + 0;
        }
        else if (Day02.unentschiedenList.contains(tokens[0]
                                                  + tokens[1])) {
            ergebnis = ergebnis
                       + 3;
        }
        else {
            ergebnis = ergebnis
                       + 6;
        }

        return ergebnis
               + Day02.punkteMap.get(tokens[1]);
    }

    private int bewerteVersuchRaetselZwei(final String[] tokens) {
        final String string;
        if (tokens[1].equals("X")) {
            // Verlieren
            string = Day02.verlorenList.stream().filter(s -> s.startsWith(tokens[0])).findFirst().orElse("");
        }
        else if (tokens[1].equals("Y")) {
            // Unentschieden
            string = Day02.unentschiedenList.stream().filter(s -> s.startsWith(tokens[0])).findFirst().orElse("");
        }
        else {
            // Gewonnen
            string = Day02.gewonnenList.stream().filter(s -> s.startsWith(tokens[0])).findFirst().orElse("");
        }

        final String[] tokensNeu = { String.valueOf(string.charAt(0)), String.valueOf(string.charAt(1)) };

        return bewerteVersuchRaetselEins(tokensNeu);
    }

    public static void main(final String[] args) {
        new Day02().raetselLoesen();
    }

    @Override
    protected String getInputFile() {
        return Day02.INPUT_TXT;
    }
}
