package learn.gomoku.game;

public class Board {

    private final char[][] board;
    private final char blackSymbol = 'X';
    private final char whiteSymbol = 'O';

    public Board(int rows, int columns) {
        board = new char[rows][columns];
    }

    public char getBlackSymbol() {
        return this.blackSymbol;
    }

    public char getWhiteSymbol() {
        return this.whiteSymbol;
    }

    public void updateBoard(Stone stone) {
        if (stone == null) {
            return;
        }

        if (board[stone.getRow()][stone.getColumn()] != '\u0000') {
            return;
        }

        board[stone.getRow()][stone.getColumn()] = stone.isBlack() ? blackSymbol : whiteSymbol;
    }

    public void printBoard() {
        // Print row index
        for (int row = 0; row < Gomoku.WIDTH; row++) {
            System.out.printf("%-2s", String.format("%02d", Gomoku.WIDTH - row));
            // Print row contents
            for (int column = 0; column < Gomoku.WIDTH; column++) {
                if (board[row][column] == '\u0000') {
                    System.out.print(" _ ");
                } else {
                    System.out.printf(" %s ", board[row][column]);
                }
            }
            System.out.println();
        }
        // Print column index
        System.out.print("   ");
        for (int i = 0; i < Gomoku.WIDTH; i++) {
            System.out.printf("%-3s", Character.toString((char) 65 + i));
        }
        System.out.println();
        System.out.println();
    }
}
