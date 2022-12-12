package de.shermanns.aoc22;

import java.util.List;
import java.util.logging.Logger;

import de.shermanns.aoc22.util.Util;

public abstract class Base {
    protected final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    protected final List<String> zeilen;

    public Base() {
        super();

        final String inputFile = getInputFile();
        this.logger.info("Lade Datei "
                         + inputFile);
        this.zeilen = Util.loadResource(this.getClass(), inputFile, this.logger);
    }

    protected abstract String getInputFile();
}
