package de.shermanns.aoc22.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

public class Util {
    private Util() {
    }

    public static List<String> loadResource(final Class<?> clazz, final String pfadString, final Logger logger) {
        try {
            final String content = IOUtils.toString(clazz.getResourceAsStream(pfadString), StandardCharsets.UTF_8);
            final String[] lines = content.split("\\r?\\n");

            System.out.println(lines.length + " Zeilen eingelesen.\n");

            return List.of(lines);
        }
        catch (final IOException e) {
            logger.severe(e.getLocalizedMessage());
        }

        return new ArrayList<>();
    }
}
