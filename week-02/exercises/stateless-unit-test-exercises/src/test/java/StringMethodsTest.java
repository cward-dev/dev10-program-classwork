import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringMethodsTest {

    StringMethods instance = new StringMethods();

    // Starts With

    @Test
    void startsShouldReturnTrueIfValue() {
        assertTrue(instance.startsWithDayOfWeek("Mon"));
        assertTrue(instance.startsWithDayOfWeek("Tues"));
        assertTrue(instance.startsWithDayOfWeek("Weds"));
        assertTrue(instance.startsWithDayOfWeek("Thurs"));
        assertTrue(instance.startsWithDayOfWeek("Fri"));
        assertTrue(instance.startsWithDayOfWeek("Sat"));
        assertTrue(instance.startsWithDayOfWeek("Sun"));
    }

    @Test
    void startsShouldReturnFalseIfCapitalizationIsWrong() {
        assertFalse(instance.startsWithDayOfWeek("mon"));
        assertFalse(instance.startsWithDayOfWeek("MOn"));
        assertFalse(instance.startsWithDayOfWeek("MON"));
        assertFalse(instance.startsWithDayOfWeek("mON"));
        assertFalse(instance.startsWithDayOfWeek("moN"));
    }

    @Test
    void startsShouldReturnFalseIfWrongValue() {
        assertFalse(instance.startsWithDayOfWeek("Tomato"));
    }

    @Test
    void startsShouldReturnTrueIfLongerWordStartingWithAbbr() {
        assertTrue(instance.startsWithDayOfWeek("Monster"));
    }

    @Test
    void startsShouldReturnFalseIfNull() {
        assertFalse(instance.startsWithDayOfWeek(null));
    }

    // Contains

    @Test
    void containsShouldReturnTrueIfValue() {
        assertTrue(instance.containsDayOfWeek("See you on Monday"));
        assertTrue(instance.containsDayOfWeek("We'll get there Tues"));
        assertTrue(instance.containsDayOfWeek("Sure, on Wedsday"));
        assertTrue(instance.containsDayOfWeek("Why not Thursday"));
        assertTrue(instance.containsDayOfWeek("Friday"));
        assertTrue(instance.containsDayOfWeek("We're going on Saturday"));
        assertTrue(instance.containsDayOfWeek("This Sunday should be fun"));
    }

    @Test
    void containsShouldReturnFalseIfCapitalizationIsWrong() {
        assertFalse(instance.containsDayOfWeek("See you on monday"));
    }

    @Test
    void containsShouldReturnFalseIfWrongValue() {
        assertFalse(instance.containsDayOfWeek("Fun Rom Toon Tow Stop"));
    }

    @Test
    void containsShouldReturnFalseIfNull() {
        assertFalse(instance.containsDayOfWeek(null));
    }

    @Test
    void removeVowelShouldReturnFalseIfNull() {
        assertEquals("",instance.removeVowelFromBetweenX(null));
    }

    @Test
    void removeVowelShouldRemoveVowel() {
        assertEquals("xx",instance.removeVowelFromBetweenX("xax"));
        assertEquals("xx",instance.removeVowelFromBetweenX("xex"));
        assertEquals("xx",instance.removeVowelFromBetweenX("xix"));
        assertEquals("xx",instance.removeVowelFromBetweenX("xox"));
        assertEquals("xx",instance.removeVowelFromBetweenX("xux"));
    }

    @Test
    void removeVowelShouldRemoveVowelInLongerSequence() {
        assertEquals("apples and xxranges",instance.removeVowelFromBetweenX("apples and xoxranges"));
    }

    @Test
    void removeVowelShouldReturnEmptyIfNull() {
        assertEquals("",instance.removeVowelFromBetweenX(null));
    }

    @Test
    void removeVowelShouldReturnEmptyIfEmpty() {
        assertEquals("",instance.removeVowelFromBetweenX(""));
    }

}