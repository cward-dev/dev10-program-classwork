import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Exercise03Test {

    @Test
    void shouldReturnTrue() {
        boolean expected = true;
        boolean actual = Exercise03.hasAllVowels("aeiou");
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnFalse() {
        boolean expected = false;
        boolean actual = Exercise03.hasAllVowels("aeior");
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnTrueWithCapitals() {
        boolean expected = true;
        boolean actual = Exercise03.hasAllVowels("AEIOU");
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnTrueWithExtras() {
        boolean expected = true;
        boolean actual = Exercise03.hasAllVowels("AEiouuufsadfs");
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnFalseIfShort() {
        boolean expected = false;
        boolean actual = Exercise03.hasAllVowels("aei");
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnFalseIfNull() {
        boolean expected = false;
        boolean actual = Exercise03.hasAllVowels(null);
        assertEquals(expected, actual);
    }

}