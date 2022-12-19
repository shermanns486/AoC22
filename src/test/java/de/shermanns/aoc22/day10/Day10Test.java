package de.shermanns.aoc22.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Day10Test {
    static Day10 day10;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        Day10Test.day10 = new Day10();
        Day10Test.day10.loadInputFile("test.txt");
    }

    @Test
    void testLoeseRaetselTeil1() {
        assertEquals(13140, Day10Test.day10.loeseRaetselTeil1());
    }
}
