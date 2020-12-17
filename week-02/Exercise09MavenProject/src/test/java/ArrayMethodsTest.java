import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayMethodsTest {

    ArrayMethods instance = new ArrayMethods();

    @Test
    void shouldReturnArrayWithAllNonZeroElements() {
        int[] values = new int[] { 0, 1, 2, 3, 4, 0, 5 };
        int[] expected = new int[] { 1, 2, 3, 4, 5 };
        int[] actual = instance.removeZero(values);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnNullIfNull() {
        int[] values = null;
        int[] expected = null;
        int[] actual = instance.removeZero(values);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyIfEmpty() {
        int[] values = new int[0];
        int[] expected = new int[0];
        int[] actual = instance.removeZero(values);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyIfOnlyZero() {
        int[] values = new int[] { 0, 0, 0 };
        int[] expected = new int[0];
        int[] actual = instance.removeZero(values);
        assertArrayEquals(expected, actual);
    }

}