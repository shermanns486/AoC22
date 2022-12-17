package de.shermanns.aoc22.day09;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SpielfeldTest {

    @Test
    void testTailHeadTouching() {
        final Spielfeld spielfeld = new Spielfeld();

        assertTrue(spielfeld.touching(new Point(0, 0), new Point(0, 0)));
        assertTrue(spielfeld.touching(new Point(1, 0), new Point(0, 0)));
        assertFalse(spielfeld.touching(new Point(1, 3), new Point(0, 1)));
        assertFalse(spielfeld.touching(new Point(1, 0), new Point(0, 2)));
    }
}
