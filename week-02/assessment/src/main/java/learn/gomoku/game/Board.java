package learn.gomoku.game;

public class Board {

    private final char[][] board;

    public Board(int rows, int columns) {
        board = new char[rows][columns];
    }

    public char[][] getBoard() {
        return this.board;
    }

    public void updateBoard(Stone stone) {
        if (stone == null) {
            return;
        }

        if (board[stone.getRow()][stone.getColumn()] != '\u0000') {
            return;
        }

        board[stone.getRow()][stone.getColumn()] = stone.isBlack() ? 'X' : 'O';
    }

    public void printBoard() {
        System.out.print("   ");
        // Print column index
        for (int i = 0; i < Gomoku.WIDTH; i++) {
            System.out.printf("%-3s", String.format("%02d", i + 1));
        }
        System.out.println();
        // Print row index
        for (int row = 0; row < Gomoku.WIDTH; row++) {
            System.out.printf("%-3s", String.format("%02d", row + 1));
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
        System.out.println();
    }
}
