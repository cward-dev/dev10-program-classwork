package learn.gomoku;

import java.util.Scanner;
import learn.gomoku.game.Gomoku;
import learn.gomoku.game.Stone;
import learn.gomoku.players.Player;
import learn.gomoku.game.Board;

public class Game {

    private final Scanner console = new Scanner(System.in);
    private final Gomoku gomoku;
    private final Board board = new Board(Gomoku.WIDTH, Gomoku.WIDTH);

    public Game(Gomoku gomoku) {
        this.gomoku = gomoku;
    }

    public boolean run() {
        System.out.println(gomoku.getCurrent().getName() + " will go first.");
        System.out.println();
        board.printBoard();

        do {
            takeTurn();
        } while (!gomoku.isOver());

        return endGame();
    }

    public void takeTurn() {
        Player currentPlayer = gomoku.getCurrent();
        Stone nextStone = currentPlayer.generateMove(gomoku.getStones());
        int row;
        int column;

        System.out.println(currentPlayer.getName() + "'s Turn");

        if (nextStone == null) {
            System.out.print("Enter a row: ");
            row = Integer.parseInt(console.nextLine()) - 1;
            System.out.print("Enter a column: ");
            column = Integer.parseInt(console.nextLine()) - 1;

            nextStone = new Stone(row, column, gomoku.isBlacksTurn());
            moveStoneIfValidMove(row, column, nextStone);
        } else {
            System.out.println("Enter a row: " + (nextStone.getRow() + 1));
            System.out.println("Enter a column: " + (nextStone.getColumn() + 1));

            moveStoneIfValidMove(nextStone.getRow(), nextStone.getColumn(), nextStone);
        }
        System.out.println();
        board.printBoard();
    }

    public void moveStoneIfValidMove(int row, int column, Stone nextStone) {
        System.out.println();
        if (board.getBoard()[row][column] == '\u0000') {
            gomoku.place(nextStone);
            board.updateBoard(nextStone);
            return;
        }

        System.out.println("[Err]: Duplicate move.");
    }

    public boolean endGame() {
        Player winner = gomoku.getWinner();
        char playAgain;

        System.out.println(winner.getName() + " wins!");
        System.out.println();
        do {
            System.out.print("Play Again? [y/n]: ");
            playAgain = console.nextLine().charAt(0);
        } while (playAgain != 'y' && playAgain != 'n');
        System.out.println();

        if (playAgain == 'y') {
            return true;
        }
        System.out.println("Goodbye!");
        return false;
    }

}
