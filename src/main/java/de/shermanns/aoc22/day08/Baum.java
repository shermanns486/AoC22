package de.shermanns.aoc22.day08;

public class Baum {
    private int hoehe;
    private boolean sichtbar;
    private int score;

    public Baum() {
        this.hoehe = 0;
        this.sichtbar = false;
        this.score = 0;
    }

    public Baum(final int hoehe, final boolean sichtbar) {
        this();

        this.hoehe = hoehe;
        this.sichtbar = sichtbar;
    }

    public int getHoehe() {
        return this.hoehe;
    }

    public void setHoehe(final int hoehe) {
        this.hoehe = hoehe;
    }

    public boolean isSichtbar() {
        return this.sichtbar;
    }

    public void setSichtbar(final boolean sichtbar) {
        this.sichtbar = sichtbar;
    }

    @Override
    public String toString() {
        return this.hoehe + (this.sichtbar ? "!" : " ") + "(" + this.score + ")";
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(final int score) {
        this.score = score;
    }
}
