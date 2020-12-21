package learn.gomoku.ui;

import java.util.Scanner;
import learn.gomoku.game.Gomoku;
import learn.gomoku.game.Result;
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
        System.out.println("( Randomizing )");
        System.out.println();
        System.out.println(gomoku.getCurrent().getName() + " will go first.");
        System.out.println();

        getAdvantage();

        do {
            takeTurn();
        } while (!gomoku.isOver());

        return endGame();
    }

    public void takeTurn() {
        boolean isValidMove;
        do {
            Player currentPlayer = gomoku.getCurrent();
            Stone nextStone = currentPlayer.generateMove(gomoku.getStones()); // If player then return null, otherwise computer controlled
            int row;
            int column;

            // Print whose turn it is
            System.out.print(currentPlayer.getName() + "'s Turn ");
            if (gomoku.isBlacksTurn()) {
                System.out.printf("(%s)%n", board.getBlackSymbol()); // Symbol for black on black's turn
            } else {
                System.out.printf("(%s)%n", board.getWhiteSymbol()); // Symbol for white on white's turn
            }

            if (nextStone == null) { // If player, get inputs
                int rowInput = -1;
                do {
                    System.out.printf("Enter a row [%d-%d]: ", 1, Gomoku.WIDTH);
                    try {
                        rowInput = Integer.parseInt(console.nextLine());
                    } catch (NumberFormatException e) {
                        // Empty Catch
                    }
                } while (rowInput < 0 || rowInput > Gomoku.WIDTH);
                row = Gomoku.WIDTH - rowInput;

                char columnInput;
                do {
                    System.out.print("Enter a column [A-O]: ");
                    columnInput = Character.toUpperCase(console.nextLine().charAt(0));
                    column = columnInput - 65;
                } while (column < 0 || column > Gomoku.WIDTH - 1);

                nextStone = new Stone(row, column, gomoku.isBlacksTurn());
            } else { // If not player, use pre-generated move as inputs
                char columnInput = (char)(65 + nextStone.getColumn());
                System.out.println("Enter a row: " + (Gomoku.WIDTH - nextStone.getRow()));
                System.out.println("Enter a column: " + columnInput);
            }
            System.out.println();

            Result result = gomoku.place(nextStone);
            getResult(result, nextStone);

            isValidMove = result.isSuccess();
        } while (!isValidMove);
    }

    public void getResult(Result result, Stone nextStone) {
        if (result.isSuccess()) {
            board.updateBoard(nextStone);
        } else {
            System.out.print("[Err]: ");
        }

        if (result.getMessage() != null) {
            System.out.println(result.getMessage());
            System.out.println();
        }

        board.printBoard();
    }

    public boolean endGame() {
        Player winner = gomoku.getWinner();
        char playAgain;

        System.out.println(winner.getName() + " has won the game! Would you like to play again?");
        System.out.println();
        do {
            System.out.print("Play Again? [y/n]: ");
            playAgain = console.nextLine().charAt(0);
            System.out.println();
        } while (playAgain != 'y' && playAgain != 'n');
        System.out.println();

        if (playAgain == 'y') {
            return true;
        }
        System.out.println("Goodbye!");
        return false;
    }

    // Advantage methods below here

    public void getAdvantage() {
        System.out.print("Would you like to play with a swap advantage rule? [y/n]: ");
        char answer = Character.toLowerCase(console.nextLine().charAt(0));
        System.out.println();

        if (answer == 'n') {
            System.out.println("You have elected to play with no advantage rule.");
            System.out.println();
            board.printBoard();
            return;
        }

        int ruleSelection = -1;
        System.out.println("Which swap advantage rule would you like to use?");
        System.out.println("1. Swap");
        System.out.println("2. Swap2");
        System.out.println("0. None");

        do {
            System.out.print("Select [1-2]: ");
            try {
                ruleSelection = Integer.parseInt(console.nextLine());
            } catch (NumberFormatException e) {
                // Empty Catch
            }
        } while (ruleSelection < 0 || ruleSelection > 2);
        System.out.println();

        switch (ruleSelection) {
            case 1:
                advantageSwap(2);
                break;
            case 2:
                advantageSwap(3);
                break;
            case 0:
                System.out.println("You have elected to play with no advantage rule.");
                System.out.println();
                board.printBoard();
                break;
        }
    }

    public void advantageSwap(int numberOfChoices) {
        System.out.println("ADVANTAGE RULES");
        System.out.println("===============");
        System.out.println("* The starting player puts the first three stones anywhere on the board (two black stones and a white one).");
        System.out.println("* The second player can decide whether s/he wants to:");
        System.out.println("     1 Stay at white and put a fourth stone or s/he can swap and control the black stones.");
        System.out.println("     2 Swap and control the black stones.");
        if (numberOfChoices == 3)
            { System.out.println("     3 Put two more stones (one black and one white) and defer choice to opponent."); }
        System.out.println("* After this ceremony the game will continue normally.");
        System.out.println();

        board.printBoard();

        executeAdvantageMoves(numberOfChoices, 3);
    }

    public void executeAdvantageMoves(int numberOfChoices, int numberOfTurns) {
        int swapToBlack = -1;

        for (int i = 0; i < numberOfTurns - 1; i++) {
            takeTurn();
            gomoku.swap();
        }
        takeTurn();

        System.out.println(gomoku.getCurrent().getName() + ", what would you like to do?");
        System.out.printf("1. Swap to black (%s) and end turn.%n", board.getBlackSymbol());
        System.out.printf("2. Remain white (%s) and place fourth stone.%n", board.getWhiteSymbol());
        if (numberOfChoices == 3) {
            System.out.println("3. Place two more stones (one black and one white) and defer choice to opponent.");
        }

        if (gomoku.getCurrent().generateMove(gomoku.getStones()) != null) {
            swapToBlack = (int)(Math.random() * numberOfChoices) + 1;
            System.out.printf("Your Choice [1-%d]: %d%n", numberOfChoices, swapToBlack);
        } else {
            do {
                System.out.printf("Select [1-%d]: ", numberOfChoices);
                try {
                    swapToBlack = Integer.parseInt(console.nextLine());
                } catch (NumberFormatException e) {
                    // Empty Catch
                }
            } while (swapToBlack < 1 || swapToBlack > numberOfChoices);
        }
        System.out.println();

        switch (swapToBlack) {
            case 1:
                System.out.println(gomoku.getCurrent().getName() + " has swapped to black. Their turn has ended.");
                System.out.println();
                gomoku.swap();
                break;
            case 2:
                System.out.println(gomoku.getCurrent().getName() + " remains white. Their turn is next.");
                System.out.println();
                break;
            case 3:
                System.out.println(gomoku.getCurrent().getName() + " defers choice. They will place the next two stones.");
                System.out.println();
                executeAdvantageMoves(2, 2);
                break;
        }
        board.printBoard();
    }
}
