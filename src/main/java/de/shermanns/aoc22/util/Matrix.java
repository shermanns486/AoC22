package de.shermanns.aoc22.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Matrix<T> {
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final String DEFAULT_SEPARATOR = " ";

    private final List<List<T>> content;

    private final int zeilenAnzahl;

    private final int spaltenAnzahl;

    public Matrix(final int zeilen, final int spalten, final Class<T> contentType) {
        this.zeilenAnzahl = zeilen;
        this.spaltenAnzahl = spalten;

        this.content = new ArrayList<>(zeilen);
        for (int i = 0; i < this.zeilenAnzahl; i++) {
            final List<T> zeile = new ArrayList<>();
            for (int j = 0; j < this.spaltenAnzahl; j++) {
                try {
                    zeile.add(contentType.getConstructor()
                            .newInstance());
                }
                catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                    this.logger.log(new LogRecord(Level.SEVERE, e.getMessage()));
                }
            }
            this.content.add(zeile);
        }
    }

    public T get(final int zeile, final int spalte) {
        return this.content.get(zeile)
                .get(spalte);
    }

    public T set(final int zeile, final int spalte, final T content) {
        final T oldContent = get(zeile, spalte);

        this.content.get(zeile)
                .set(spalte, content);

        return oldContent;
    }

    public List<List<T>> getContent() {
        return this.content;
    }

    public List<T> getZeile(final int zeilenIndex) {
        return this.content.get(zeilenIndex);
    }

    public List<T> getSpalte(final int spaltenIndex) {
        final List<T> spalte = new ArrayList<>();

        for (int zeilenIndex = 0; zeilenIndex < this.zeilenAnzahl; zeilenIndex++) {
            spalte.add(this.content.get(zeilenIndex)
                    .get(spaltenIndex));
        }

        return spalte;
    }

    public String print() {
        return print(Matrix.DEFAULT_SEPARATOR);
    }

    public String print(final String separator) {
        final StringBuilder builder = new StringBuilder();
        builder.append(System.lineSeparator())
                .append(System.lineSeparator());

        for (int zeilenIndex = 0; zeilenIndex < this.zeilenAnzahl; zeilenIndex++) {
            for (int spaltenIndex = 0; spaltenIndex < this.spaltenAnzahl; spaltenIndex++) {
                builder.append(this.content.get(zeilenIndex)
                        .get(spaltenIndex))
                        .append(separator);
            }

            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    public int getZeilenAnzahl() {
        return this.zeilenAnzahl;
    }

    public int getSpaltenAnzahl() {
        return this.spaltenAnzahl;
    }
}
