import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Exercise06Test {

    Exercise06 instance = new Exercise06();

    // Suggested test additions:
    // shouldBeNullForNulLArg
    // shouldCapitalizeMultipleElements (several scenarios)
    // shouldIgnoreNullElements
    // shouldIgnoreEmptyElements

    @Test
    void shouldCapitalizeOneElement() {
        String[] values = {"lower"};
        String[] expected = {"Lower"};
        String[] actual = instance.capitalizeAll(values);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldBeEmptyForEmptyArg() {
        assertArrayEquals(new String[0], instance.capitalizeAll(new String[0]));
    }

    @Test
    void shouldBeNullForNulLArg() {
        assertArrayEquals(null, instance.capitalizeAll(null));
    }

    @Test
    void shouldCapitalizeMultipleElements() {
        assertArrayEquals(new String[] { "Fluoride", "Bike", "Flower" },
                instance.capitalizeAll(new String[] { "fluoride", "bike", "flower" }));
        assertArrayEquals(new String[] { "Fluoride", "Bike", "FloWer" },
                instance.capitalizeAll(new String[] { "fluoride", "bike", "floWer" }));
        assertArrayEquals(new String[] { "Fluoride", "Bike", "0floWer" },
                instance.capitalizeAll(new String[] { "fluoride", "bike", "0floWer" }));
    }

    @Test
    void shouldIgnoreNullElements() {
        String[] values = {"burrito", null, "taco"};
        String[] expected = {"Burrito", null, "Taco"};
        String[] actual = instance.capitalizeAll(values);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldIgnoreEmptyElements() {
        assertArrayEquals(new String[] { "Flower", "", "Fluoride" },
                instance.capitalizeAll(new String[] { "flower", "", "fluoride" }));
    }

    @Test
    void shouldCapitalizeMultipleWordsInOneElement() {
        assertArrayEquals(new String[] { "Bears Beets Battlestar Galactica" },
                instance.capitalizeAll(new String[] { "bears beets battlestar galactica" }));
    }
}