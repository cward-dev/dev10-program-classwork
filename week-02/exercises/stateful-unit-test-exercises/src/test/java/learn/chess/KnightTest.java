package learn.chess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

    Knight knight = new Knight();

    @Test
    void shouldMoveToFourCorners() {
        knight.setLocation(2,3); // bottom left;
        assertTrue(knight.move(0, 0));
        assertEquals(0, knight.getRow());
        assertEquals(0, knight.getColumn());

        knight.setLocation(2,4); // bottom right;
        assertTrue(knight.move(0, 7));
        assertEquals(0, knight.getRow());
        assertEquals(7, knight.getColumn());

        knight.setLocation(4,2); // top left;
        assertTrue(knight.move(7, 0));
        assertEquals(7, knight.getRow());
        assertEquals(0, knight.getColumn());

        knight.setLocation(5,4); // top right;
        assertTrue(knight.move(7, 7));
        assertEquals(7, knight.getRow());
        assertEquals(7, knight.getColumn());
    }

    @Test
    void shouldMoveHorizontalThreeVerticalTwo() {
        knight.setLocation(4, 4);
        assertTrue(knight.move(1, 6));
        assertEquals(1, knight.getRow());
        assertEquals(6, knight.getColumn());
    }

    @Test
    void shouldMoveHorizontalTwoVerticalThree() {
        knight.setLocation(4, 4);
        assertTrue(knight.move(2, 7));
        assertEquals(2, knight.getRow());
        assertEquals(7, knight.getColumn());
    }

    @Test
    void shouldNotMoveOffBoard() {
        knight.setLocation(1,2); // off bottom left;
        assertFalse(knight.move(-1, -1));
        assertEquals(1, knight.getRow());
        assertEquals(2, knight.getColumn());

        knight.setLocation(1,5); // off bottom right;
        assertFalse(knight.move(-1, 8));
        assertEquals(1, knight.getRow());
        assertEquals(5, knight.getColumn());

        knight.setLocation(5,1); // off top left;
        assertFalse(knight.move(8, -1));
        assertEquals(5, knight.getRow());
        assertEquals(1, knight.getColumn());

        knight.setLocation(5,5); // off top right;
        assertFalse(knight.move(8, 8));
        assertEquals(5, knight.getRow());
        assertEquals(5, knight.getColumn());
    }

    @Test
    void shouldNotMoveToExistingLocation() {
        knight.setLocation(4, 5);

        assertFalse(knight.move(4, 5)); // same as current location;
        assertEquals(4, knight.getRow());
        assertEquals(5, knight.getColumn());
    }

    @Test
    void shouldBeAbleToMoveAfterInvalidMove() {
        knight.setLocation(4, 5);

        assertFalse(knight.move(4, 2)); // invalid move;
        assertEquals(4, knight.getRow());
        assertEquals(5, knight.getColumn());

        assertFalse(knight.move(2, 5)); // invalid move;
        assertEquals(4, knight.getRow());
        assertEquals(5, knight.getColumn());

        assertTrue(knight.move(2, 2)); // valid move;
        assertEquals(2, knight.getRow());
        assertEquals(2, knight.getColumn());
    }

}