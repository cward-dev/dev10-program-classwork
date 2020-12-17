import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Exercise05Test {

    @Test
    void shouldReturnTrue() {
        assertTrue(Exercise05.isWithinFiveOfAHundred(-205));
        assertTrue(Exercise05.isWithinFiveOfAHundred(-96));
        assertTrue(Exercise05.isWithinFiveOfAHundred(-3));
        assertTrue(Exercise05.isWithinFiveOfAHundred(0));
        assertTrue(Exercise05.isWithinFiveOfAHundred(4));
        assertTrue(Exercise05.isWithinFiveOfAHundred(96));
        assertTrue(Exercise05.isWithinFiveOfAHundred(197));
        assertTrue(Exercise05.isWithinFiveOfAHundred(204));
        assertTrue(Exercise05.isWithinFiveOfAHundred(200));
        assertTrue(Exercise05.isWithinFiveOfAHundred(131499));
    }

    @Test
    void shouldReturnFalse() {
        assertFalse(Exercise05.isWithinFiveOfAHundred(-208));
        assertFalse(Exercise05.isWithinFiveOfAHundred(-94));
        assertFalse(Exercise05.isWithinFiveOfAHundred(-15));
        assertFalse(Exercise05.isWithinFiveOfAHundred(7));
        assertFalse(Exercise05.isWithinFiveOfAHundred(92));
        assertFalse(Exercise05.isWithinFiveOfAHundred(109));
        assertFalse(Exercise05.isWithinFiveOfAHundred(191));
        assertFalse(Exercise05.isWithinFiveOfAHundred(413412478));
    }

}