package learn.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayMethodsTest {

    @Test
    void shiftLeftShouldReturnNullWhenNull() {
        int[] actual = ArrayMethods.shiftLeft(null);
        assertNull(actual);
    }

    @Test
    void shiftLeftShouldReturnEmptyArrayWhenEmpty() {
        int[] actual = ArrayMethods.shiftLeft(new int[]{});
        assertEquals(0, actual.length);
    }

    @Test
    void shiftLeftShouldReturnNewArray() {
        int[] input = {1, 2, 3};
        int[] actual = ArrayMethods.shiftLeft(input);
        assertFalse(input == actual); // must be a different reference
    }

    @Test
    void shiftLeftShouldNotShiftOneElement() {
        int[] expected = {5};
        int[] actual = ArrayMethods.shiftLeft(new int[]{5});
        assertArrayEquals(expected, actual);
    }

    @Test
    void shiftLeftShouldShift() {
        assertArrayEquals(new int[]{2, 3, 1}, ArrayMethods.shiftLeft(new int[]{1, 2, 3}));
        assertArrayEquals(new int[]{5, 4}, ArrayMethods.shiftLeft(new int[]{4, 5}));
        assertArrayEquals(new int[]{9, 8, 9, 8}, ArrayMethods.shiftLeft(new int[]{8, 9, 8, 9}));
        assertArrayEquals(new int[]{2, 100, 2}, ArrayMethods.shiftLeft(new int[]{2, 2, 100}));
    }
}