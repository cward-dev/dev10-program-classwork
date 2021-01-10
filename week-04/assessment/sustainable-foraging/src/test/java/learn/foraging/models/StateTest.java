package learn.foraging.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    @Test
    void shouldGetWisconsinFromAbbreviation() {
        State expected = State.WISCONSIN;
        State actual = State.getStateFromAbbreviation("WI");

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetWisconsinFromAbbreviationLowercase() {
        State expected = State.WISCONSIN;
        State actual = State.getStateFromAbbreviation("wi");

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnNullIfInvalidAbbreviation() {
        State actual = State.getStateFromAbbreviation("zz");

        assertNull(actual);
    }

    @Test
    void shouldGetWisconsinFromStateName() {
        State expected = State.WISCONSIN;
        State actual = State.getStateFromName("Wisconsin");

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetWisconsinFromStateNameLowercase() {
        State expected = State.WISCONSIN;
        State actual = State.getStateFromName("wisconsin");

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnNullIfInvalidStateName() {
        State actual = State.getStateFromName("La La Land");

        assertNull(actual);
    }



}