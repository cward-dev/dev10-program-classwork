import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Exercise07Test {

    Exercise07 instance = new Exercise07();

    @Test
    void shouldReturnReversedArrayIfValid() {
        // Act
        String[] values = new String[] { "a" };
        String[] expected = { "c", "b", "a" };
        String[] actual = instance.reverse(values);
        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnNewIdenticalArrayIfLengthOfOne() {
        // Act
        String[] values = new String[] { "a" };
        String[] expected = { "a" };
        String[] actual = instance.reverse(values);
        // Assert
        assertArrayEquals(expected, actual);
        assertNotSame(values, actual);
    }

    @Test
    void shouldReturnNullIfNull() {
        // Act
        String[] values = null;
        String[] expected = null;
        String[] actual = instance.reverse(values);
        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyIfEmpty() {
        // Act
        String[] values = new String[0];
        String[] expected = new String [0];
        String[] actual = instance.reverse(values);
        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyValueIfEmptyValue() {
        // Act
        String[] values = new String[] { "" };
        String[] expected = new String[] { "" };
        String[] actual = instance.reverse(values);
        // Assert
        assertArrayEquals(expected, actual);
    }

}