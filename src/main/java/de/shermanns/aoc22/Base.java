package de.shermanns.aoc22;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import de.shermanns.aoc22.util.Util;

public abstract class Base {
    protected final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    protected final List<String> zeilen = new ArrayList<>();

    protected Base() {
        loadInputFile(getInputFile());
    }

    public void loadInputFile(final String inputFile) {
        System.out.print("Lade Datei " + inputFile + "... ");

        this.zeilen.clear();
        this.zeilen.addAll(Util.loadResource(this.getClass(), inputFile, this.logger));
    }

    protected abstract String getInputFile();
}
