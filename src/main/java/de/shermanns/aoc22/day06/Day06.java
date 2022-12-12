package de.shermanns.aoc22.day06;

import java.util.logging.Level;

import org.apache.commons.lang3.StringUtils;

import de.shermanns.aoc22.Base;

public class Day06 extends Base {
    private static final String INPUT_TXT = "input.txt";

    public static void main(final String[] args) {
        new Day06().loeseRaetsel();
    }

    private void loeseRaetsel() {
        final String nachricht = this.zeilen.get(0);

        int index1 = 4;
        boolean marker1Gefunden = false;
        String packet1 = lesePacket(nachricht, index1, 4);

        while (!(marker1Gefunden
                 || packet1.isEmpty())) {
            if (this.logger.isLoggable(Level.INFO)) {
                this.logger.info("Packet 1 ["
                                 + (index1
                                    - 3)
                                 + "]: "
                                 + packet1);
            }

            marker1Gefunden = markerGefunden(packet1, 4);

            if (!marker1Gefunden) {
                index1++;
                packet1 = lesePacket(nachricht, index1, 4);
            }
        }

        int index2 = 14;
        boolean marker2Gefunden = false;
        String packet2 = lesePacket(nachricht, index2, 14);

        while (!(marker2Gefunden
                 || packet2.isEmpty())) {
            if (this.logger.isLoggable(Level.INFO)) {
                this.logger.info("Packet 2 ["
                                 + (index2
                                    - 14)
                                 + "]: "
                                 + packet2);
            }

            marker2Gefunden = markerGefunden(packet2, 14);

            if (!marker2Gefunden) {
                index2++;
                packet2 = lesePacket(nachricht, index2, 14);
            }
        }

        if (this.logger.isLoggable(Level.INFO)) {
            this.logger.info("Ergebnis Rätsel 1: "
                             + index1
                             + " ("
                             + packet1
                             + ")");
            this.logger.info("Ergebnis Rätsel 2: "
                             + index2
                             + " ("
                             + packet2
                             + ")");
        }
    }

    private boolean markerGefunden(final String packet, final int schrittweite) {
        int anzahl = 0;
        for (int i = 0; i < schrittweite; i++) {
            anzahl += StringUtils.countMatches(packet, packet.charAt(i));
        }

        return anzahl == schrittweite;
    }

    private String lesePacket(final String nachricht, final int index, final int schrittweite) {
        if (index > nachricht.length()) {
            return "";
        }

        return nachricht.substring(index
                                   - schrittweite,
                index);
    }

    @Override
    protected String getInputFile() {
        return Day06.INPUT_TXT;
    }
}
