package de.shermanns.aoc22.day08;

import java.util.List;
import java.util.logging.Level;

import de.shermanns.aoc22.Base;
import de.shermanns.aoc22.util.Matrix;

public class Day08 extends Base {
    private static final String INPUT_TXT = "input.txt";

    Matrix<Baum> plantage;

    public static void main(final String[] args) {
        final Day08 day08 = new Day08();

        day08.createPlantage();

        final int ergebnisTeil1 = day08.ermittleSichtbarkeit();

        final int ergebnisTeil2 = day08.ermittleScore();

        day08.logger.log(Level.INFO, "{0}", day08.plantage.print());

        day08.logger.log(Level.INFO, "Ergebnis Teil 1: {0}", ergebnisTeil1);
        day08.logger.log(Level.INFO, "Ergebnis Teil 2: {0}", ergebnisTeil2);
    }

    private int ermittleScore() {
        final int zeilenAnzahl = this.plantage.getZeilenAnzahl();
        final int spaltenAnzahl = this.plantage.getSpaltenAnzahl();

        int highScore = 0;

        for (int zeilenIndex = 0; zeilenIndex < zeilenAnzahl; zeilenIndex++) {
            for (int spaltenIndex = 0; spaltenIndex < spaltenAnzahl; spaltenIndex++) {
                final int oben = blickNachOben(zeilenIndex, spaltenIndex);
                final int unten = blickNachUnten(zeilenIndex, spaltenIndex);
                final int rechts = blickNachRechts(zeilenIndex, spaltenIndex);
                final int links = blickNachLinks(zeilenIndex, spaltenIndex);

                final int score = oben
                                  * unten
                                  * rechts
                                  * links;

                if (score > highScore) {
                    highScore = score;
                }

                this.logger.log(Level.INFO, "{0}", String.format("[%d][%d]: %d * %d * %d * %d = %d", zeilenIndex,
                        spaltenIndex, oben, unten, rechts, links, score));

                this.plantage.get(zeilenIndex, spaltenIndex).setScore(score);
            }
        }

        return highScore;
    }

    private int blickNachLinks(final int zeilenIndex, final int spaltenIndex) {
        if (spaltenIndex == 0) {
            return 0;
        }

        int score = 0;
        final int hoehe = this.plantage.get(zeilenIndex, spaltenIndex).getHoehe();

        for (int s = spaltenIndex
                     - 1; s >= 0; s--) {
            final int hoehe2 = this.plantage.get(zeilenIndex, s).getHoehe();

            if (hoehe2 >= hoehe) {
                score++;
                break;
            }
            else {
                score++;
            }
        }

        return score;
    }

    private int blickNachRechts(final int zeilenIndex, final int spaltenIndex) {
        if (spaltenIndex == this.plantage.getSpaltenAnzahl()
                            - 1) {
            return 0;
        }

        int score = 0;
        final int hoehe = this.plantage.get(zeilenIndex, spaltenIndex).getHoehe();

        for (int s = spaltenIndex
                     + 1; s < this.plantage.getSpaltenAnzahl(); s++) {
            final int hoehe2 = this.plantage.get(zeilenIndex, s).getHoehe();

            if (hoehe2 >= hoehe) {
                score++;
                break;
            }
            else {
                score++;
            }
        }

        return score;
    }

    private int blickNachOben(final int zeilenIndex, final int spaltenIndex) {
        if (zeilenIndex == 0) {
            return 0;
        }

        int score = 0;
        final int hoehe = this.plantage.get(zeilenIndex, spaltenIndex).getHoehe();

        for (int z = zeilenIndex
                     - 1; z >= 0; z--) {
            final int hoehe2 = this.plantage.get(z, spaltenIndex).getHoehe();

            if (hoehe2 >= hoehe) {
                score++;
                break;
            }
            else {
                score++;
            }
        }

        return score;
    }

    private int blickNachUnten(final int zeilenIndex, final int spaltenIndex) {
        if (zeilenIndex == this.plantage.getZeilenAnzahl()
                           - 1) {
            return 0;
        }

        int score = 0;
        final int hoehe = this.plantage.get(zeilenIndex, spaltenIndex).getHoehe();

        for (int z = zeilenIndex
                     + 1; z < this.plantage.getZeilenAnzahl(); z++) {
            final int hoehe2 = this.plantage.get(z, spaltenIndex).getHoehe();

            if (hoehe2 >= hoehe) {
                score++;
                break;
            }
            else {
                score++;
            }
        }

        return score;
    }

    private int ermittleSichtbarkeit() {
        final int zeilenAnzahl = this.plantage.getZeilenAnzahl();
        final int spaltenAnzahl = this.plantage.getSpaltenAnzahl();

        // Richtung Osten
        for (int zeilenIndex = 0; zeilenIndex < zeilenAnzahl; zeilenIndex++) {
            int maxHoehe = this.plantage.get(zeilenIndex, 0).getHoehe();

            for (int spaltenIndex = 0; spaltenIndex < spaltenAnzahl; spaltenIndex++) {
                maxHoehe = checkBaumAt(zeilenIndex, spaltenIndex, maxHoehe);
            }
        }

        // Richtung Westen
        for (int zeilenIndex = 0; zeilenIndex < zeilenAnzahl; zeilenIndex++) {
            int maxHoehe = this.plantage.get(zeilenIndex, spaltenAnzahl
                                                          - 1)
                    .getHoehe();

            for (int spaltenIndex = spaltenAnzahl
                                    - 1; spaltenIndex > 0; spaltenIndex--) {
                maxHoehe = checkBaumAt(zeilenIndex, spaltenIndex, maxHoehe);
            }
        }

        // Richtung Süden
        for (int spaltenIndex = 0; spaltenIndex < spaltenAnzahl; spaltenIndex++) {
            int maxHoehe = this.plantage.get(0, spaltenIndex).getHoehe();

            for (int zeilenIndex = 0; zeilenIndex < zeilenAnzahl; zeilenIndex++) {
                maxHoehe = checkBaumAt(zeilenIndex, spaltenIndex, maxHoehe);
            }
        }

        // Richtung Norden
        for (int spaltenIndex = 0; spaltenIndex < spaltenAnzahl; spaltenIndex++) {
            int maxHoehe = this.plantage.get(zeilenAnzahl
                                             - 1,
                    spaltenIndex).getHoehe();

            for (int zeilenIndex = zeilenAnzahl
                                   - 1; zeilenIndex > 0; zeilenIndex--) {
                maxHoehe = checkBaumAt(zeilenIndex, spaltenIndex, maxHoehe);
            }
        }

        return ermittleAnzahlSichtbareBaeume();
    }

    private int checkBaumAt(final int zeilenIndex, final int spaltenIndex, int maxHoehe) {
        final Baum baum = this.plantage.get(zeilenIndex, spaltenIndex);
        final int hoehe = baum.getHoehe();

        if (hoehe > maxHoehe) {
            baum.setSichtbar(true);
            maxHoehe = hoehe;
        }

        return maxHoehe;
    }

    public int ermittleAnzahlSichtbareBaeume() {
        int sichtbareBaeume = 0;

        for (final List<Baum> baeume : this.plantage.getContent()) {
            for (final Baum baum : baeume) {
                if (baum.isSichtbar()) {
                    sichtbareBaeume++;
                }
            }
        }

        return sichtbareBaeume;
    }

    private void createPlantage() {
        // Plantage initialisieren
        final int zeilenAnzahl = this.zeilen.size();
        final int spaltenAnzahl = this.zeilen.get(0).length();

        this.plantage = new Matrix<>(zeilenAnzahl, spaltenAnzahl, Baum.class);

        for (int zeilenIndex = 0; zeilenIndex < zeilenAnzahl; zeilenIndex++) {
            final String zeile = this.zeilen.get(zeilenIndex);

            for (int spaltenIndex = 0; spaltenIndex < spaltenAnzahl; spaltenIndex++) {
                final int hoehe = Integer.parseInt(zeile.substring(spaltenIndex, spaltenIndex
                                                                                 + 1));
                final boolean sichtbar = zeilenIndex == 0
                                         || spaltenIndex == 0
                                         || zeilenIndex == zeilenAnzahl
                                                           - 1
                                         || spaltenIndex == spaltenAnzahl
                                                            - 1;

                this.plantage.set(zeilenIndex, spaltenIndex, new Baum(hoehe, sichtbar));
            }
        }
    }

    @Override
    protected String getInputFile() {
        return Day08.INPUT_TXT;
    }
}
