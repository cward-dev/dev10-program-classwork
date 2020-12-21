package learn.gomoku;

import java.util.Scanner;
import learn.gomoku.game.Gomoku;
import learn.gomoku.players.HumanPlayer;
import learn.gomoku.players.Player;
import learn.gomoku.players.RandomPlayer;
import learn.gomoku.players.SmarterPlayer;

public class SetUp {

    private final Scanner console = new Scanner(System.in);

    public Gomoku run() {
        System.out.println("Welcome to Gomoku");
        System.out.println("=================");
        System.out.println();

        Player playerOne = newPlayer(1);
        Player playerTwo = newPlayer(2);

        return new Gomoku(playerOne, playerTwo);
    }
    
    public Player newPlayer(int playerNumber) {
        Player player = null;
        int userSelection = -1;

        System.out.println("Player " + playerNumber + " is:");
        System.out.println("1. Human");
        System.out.println("2. Random Player");
        System.out.println("3. Smart(er) Player");
        do {
            System.out.print("Select [1-3]: ");
            try {
                userSelection = Integer.parseInt(console.nextLine());
            } catch (NumberFormatException e) {
                // Empty Catch
            }
        } while (userSelection < 1 || userSelection > 3);
        System.out.println();

        switch (userSelection) {
            case 1:
                String playerName;
                do {
                    System.out.print("Player " + playerNumber + ", enter your name: ");
                    playerName = console.nextLine();
                } while (playerName.isBlank());
                player = new HumanPlayer(playerName);
                break;
            case 2:
                player = new RandomPlayer();
                System.out.print("Player " + playerNumber + ", enter your name: ");
                System.out.println(player.getName());
                break;
            case 3:
                player = new SmarterPlayer();
                System.out.print("Player " + playerNumber + ", enter your name: ");
                System.out.println(player.getName());
                break;

        }
        System.out.println();
        System.out.println("Welcome to the game, " + player.getName() + "!");
        System.out.println();

        return player;
    }
}
