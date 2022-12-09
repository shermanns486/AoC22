package de.shermanns.aoc22.day01;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import de.shermanns.aoc22.Base;
import de.shermanns.aoc22.util.Util;

public class Day01 extends Base {
    private static final String INPUT_TXT = "input.txt";

    private void raetselEinsLoesen() {
        this.logger.info("Lese Datei " + Day01.INPUT_TXT + " ein");

        final List<String> zeilenList = Util.loadResource(getClass(), Day01.INPUT_TXT, this.logger);

        final List<Integer> proviantList = new LinkedList<>();
        int proviantProElf = 0;

        for (final String zeile : zeilenList) {
            if (StringUtils.isBlank(zeile)) {
                proviantList.add(proviantProElf);
                proviantProElf = 0;
            }
            else {
                proviantProElf = proviantProElf + Integer.parseInt(zeile);
            }
        }

        Collections.sort(proviantList);
        Collections.reverse(proviantList);

        this.logger.info(() -> "Ergebnis Rätsel 1: " + proviantList.get(0));

        //
        // Rätsel 2
        //
        raetselZweiLoesen(proviantList);
    }

    private void raetselZweiLoesen(final List<Integer> proviantList) {
        final int ergebnis = proviantList.get(0) + proviantList.get(1) + proviantList.get(2);

        this.logger.info(() -> "Ergebnis Rätsel 2: " + ergebnis);
    }

    public static void main(final String[] args) {
        new Day01().raetselEinsLoesen();
    }
}
