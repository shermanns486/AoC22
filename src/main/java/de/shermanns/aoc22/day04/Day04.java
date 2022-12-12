package de.shermanns.aoc22.day04;

import java.util.logging.Level;

import de.shermanns.aoc22.Base;

public class Day04 extends Base {
    private static final String INPUT_TXT = "input.txt";

    public static void main(final String[] args) {
        new Day04().loeseRaetsel();
    }

    private void loeseRaetsel() {
        int ergebnisRaetselEins = 0;
        int ergebnisRaetselZwei = 0;

        for (final String zeile : this.zeilen) {
            final String[] paar = zeile.split(",");

            final String[] elf1 = paar[0].split("-");
            final String[] elf2 = paar[1].split("-");

            final int sektion1Start = Integer.parseInt(elf1[0]);
            final int sektion1Ende = Integer.parseInt(elf1[1]);
            final int sektion2Start = Integer.parseInt(elf2[0]);
            final int sektion2Ende = Integer.parseInt(elf2[1]);

            // Bereiche, die vollständig enthalten sind
            if (sektion1Start <= sektion2Start
                && sektion1Ende >= sektion2Ende
                || sektion2Start <= sektion1Start
                   && sektion2Ende >= sektion1Ende) {
                ergebnisRaetselEins++;
            }

            // Bereiche, die einander überlappen
            if (sektion1Start <= sektion2Start
                && sektion1Ende >= sektion2Start
                || sektion1Start <= sektion2Ende
                   && sektion1Ende >= sektion2Ende
                || sektion1Start <= sektion2Start
                   && sektion1Ende >= sektion2Ende
                || sektion1Start >= sektion2Start
                   && sektion1Ende <= sektion2Ende) {
                ergebnisRaetselZwei++;
            }
        }

        if (this.logger.isLoggable(Level.INFO)) {
            this.logger.info("Ergebnis Rätsel 1: "
                             + ergebnisRaetselEins);
            this.logger.info("Ergebnis Rätsel 2: "
                             + ergebnisRaetselZwei);
        }
    }

    @Override
    protected String getInputFile() {
        return Day04.INPUT_TXT;
    }
}
