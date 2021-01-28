package learn.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringMethodsTest {

    @Test
    void countDigits() {
        int actual = StringMethods.countDigits(null);
        assertEquals(0, actual, "A null string should return 0 counted digits.");

        actual = StringMethods.countDigits("");
        assertEquals(0, actual, "An empty string should return 0 counted digits.");

        actual = StringMethods.countDigits("There are no digits in this string.");
        assertEquals(0, actual, "A string without digits should return 0 counted digits.");

        actual = StringMethods.countDigits("5");
        assertEquals(1, actual, "The string \"5\" should return 1 counted digit.");

        actual = StringMethods.countDigits("There are 12 sandwiches.");
        assertEquals(2, actual, "\"There are 12 sandwiches.\" should return 2 counted digits.");
    }
}