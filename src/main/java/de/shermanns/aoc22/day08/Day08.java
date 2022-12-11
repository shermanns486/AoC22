package de.shermanns.aoc22.day08;

import java.util.List;
import java.util.logging.Level;

import de.shermanns.aoc22.Base;
import de.shermanns.aoc22.util.Matrix;
import de.shermanns.aoc22.util.Util;

public class Day08 extends Base {
    private static final String INPUT_TXT = "input.txt";

    Matrix<Baum> plantage;

    public static void main(final String[] args) {
        final Day08 day08 = new Day08();

        day08.logger.info("Lade Datei " + Day08.INPUT_TXT);
        final List<String> zeilen = Util.loadResource(day08.getClass(), Day08.INPUT_TXT, day08.logger);

        day08.createPlantage(zeilen);

        final int ergebnisTeil1 = day08.ermittleSichtbarkeit();

        day08.logger.log(Level.INFO, "{0}", day08.plantage.print());

        day08.logger.log(Level.INFO, "Ergebnis Teil 1: {0}", ergebnisTeil1);
    }

    private int ermittleSichtbarkeit() {
        final int zeilenAnzahl = this.plantage.getZeilenAnzahl();
        final int spaltenAnzahl = this.plantage.getSpaltenAnzahl();

        // Richtung Osten
        for (int zeilenIndex = 0; zeilenIndex < zeilenAnzahl; zeilenIndex++) {
            int maxHoehe = this.plantage.get(zeilenIndex, 0)
                    .getHoehe();

            for (int spaltenIndex = 0; spaltenIndex < spaltenAnzahl; spaltenIndex++) {
                maxHoehe = checkBaumAt(zeilenIndex, spaltenIndex, maxHoehe);
            }
        }

        // Richtung Westen
        for (int zeilenIndex = 0; zeilenIndex < zeilenAnzahl; zeilenIndex++) {
            int maxHoehe = this.plantage.get(zeilenIndex, spaltenAnzahl - 1)
                    .getHoehe();

            for (int spaltenIndex = spaltenAnzahl - 1; spaltenIndex > 0; spaltenIndex--) {
                maxHoehe = checkBaumAt(zeilenIndex, spaltenIndex, maxHoehe);
            }
        }

        // Richtung Süden
        for (int spaltenIndex = 0; spaltenIndex < spaltenAnzahl; spaltenIndex++) {
            int maxHoehe = this.plantage.get(0, spaltenIndex)
                    .getHoehe();

            for (int zeilenIndex = 0; zeilenIndex < zeilenAnzahl; zeilenIndex++) {
                maxHoehe = checkBaumAt(zeilenIndex, spaltenIndex, maxHoehe);
            }
        }

        // Richtung Norden
        for (int spaltenIndex = 0; spaltenIndex < spaltenAnzahl; spaltenIndex++) {
            int maxHoehe = this.plantage.get(zeilenAnzahl - 1, spaltenIndex)
                    .getHoehe();

            for (int zeilenIndex = zeilenAnzahl - 1; zeilenIndex > 0; zeilenIndex--) {
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

    protected boolean istAufsteigend(final List<Integer> zeile) {
        for (int i = zeile.size() - 1; i > 0; i--) {
            if (zeile.get(i - 1)
                    .compareTo(zeile.get(i)) >= 0) {
                return false;
            }
        }
        return true;
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

    private void createPlantage(final List<String> zeilen) {
        // Plantage initialisieren
        final int zeilenAnzahl = zeilen.size();
        final int spaltenAnzahl = zeilen.get(0)
                .length();

        this.plantage = new Matrix<>(zeilenAnzahl, spaltenAnzahl, Baum.class);

        for (int zeilenIndex = 0; zeilenIndex < zeilenAnzahl; zeilenIndex++) {
            final String zeile = zeilen.get(zeilenIndex);

            for (int spaltenIndex = 0; spaltenIndex < spaltenAnzahl; spaltenIndex++) {
                final int hoehe = Integer.parseInt(zeile.substring(spaltenIndex, spaltenIndex + 1));
                final boolean sichtbar = zeilenIndex == 0
                        || spaltenIndex == 0
                        || zeilenIndex == zeilenAnzahl - 1
                        || spaltenIndex == spaltenAnzahl - 1;

                this.plantage.set(zeilenIndex, spaltenIndex, new Baum(hoehe, sichtbar));
            }
        }
    }
}
