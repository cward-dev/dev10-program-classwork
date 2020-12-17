package learn.chess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

    Queen queen = new Queen();

    @Test
    void shouldMoveToFourCorners() {
        assertTrue(queen.move(7, 0)); // top left;
        assertEquals(7, queen.getRow());
        assertEquals(0, queen.getColumn());

        assertTrue(queen.move(7, 7)); // top right;
        assertEquals(7, queen.getRow());
        assertEquals(7, queen.getColumn());

        assertTrue(queen.move(0, 7)); // bottom right;
        assertEquals(0, queen.getRow());
        assertEquals(7, queen.getColumn());

        assertTrue(queen.move(0, 0)); // bottom left;
        assertEquals(0, queen.getRow());
        assertEquals(0, queen.getColumn());
    }

    // 1. Add tests to validate Queen movement.
    // Required tests:
    // - anything off the board is invalid, should return false and leave field values alone.
    // - requesting the existing location should return false and leave field values alone.
    // - should still be able to move after an invalid move.
    // - can move diagonally in various ways
    // Always confirm that fields have been properly updated using getters.

    @Test
    void shouldNotMoveOffBoard() {
        assertFalse(queen.move(8, 0)); // off up;
        assertEquals(0, queen.getRow());
        assertEquals(0, queen.getColumn());

        assertFalse(queen.move(0, -1)); // off left;
        assertEquals(0, queen.getRow());
        assertEquals(0, queen.getColumn());

        assertFalse(queen.move(-1, 0)); // off down;
        assertEquals(0, queen.getRow());
        assertEquals(0, queen.getColumn());

        assertFalse(queen.move(0, 8)); // off right;
        assertEquals(0, queen.getRow());
        assertEquals(0, queen.getColumn());
    }

    @Test
    void shouldNotMoveToExistingLocation() {
        queen.setRow(4);
        queen.setColumn(5);

        assertFalse(queen.move(4, 5)); // same as current location;
        assertEquals(4, queen.getRow());
        assertEquals(5, queen.getColumn());
    }

    @Test
    void shouldBeAbleToMoveAfterInvalidMove() {
        queen.setRow(4);
        queen.setColumn(5);

        assertFalse(queen.move(4, -1)); // invalid move;
        assertEquals(4, queen.getRow());
        assertEquals(5, queen.getColumn());

        assertFalse(queen.move(2, 4)); // invalid move;
        assertEquals(4, queen.getRow());
        assertEquals(5, queen.getColumn());

        assertTrue(queen.move(5, 6)); // valid diagonal move;
        assertEquals(5, queen.getRow());
        assertEquals(6, queen.getColumn());
    }

    @Test
    void shouldBeAbleToMoveDiagonally() {
        queen.setRow(3);
        queen.setColumn(3);

        assertTrue(queen.move(6, 6)); // up right
        assertEquals(6, queen.getRow());
        assertEquals(6, queen.getColumn());

        queen.setRow(3);
        queen.setColumn(3);
        assertTrue(queen.move(2, 2)); // down left
        assertEquals(2, queen.getRow());
        assertEquals(2, queen.getColumn());

        queen.setRow(3);
        queen.setColumn(3);
        assertTrue(queen.move(1, 5)); // down right
        assertEquals(1, queen.getRow());
        assertEquals(5, queen.getColumn());

        queen.setRow(3);
        queen.setColumn(3);
        assertTrue(queen.move(5, 1)); // down left
        assertEquals(5, queen.getRow());
        assertEquals(1, queen.getColumn());
    }
}