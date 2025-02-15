import learn.BoardGame;
import learn.GameRepository;

import java.util.ArrayList;

public class Exercise06 {

    public static void main(String[] args) {

        ArrayList<BoardGame> games = GameRepository.getAll();

        // 1. Use a loop to find the game in `games` that can be played by the most players.
        // 2. Print the game. (Expected: "Ultimate Werewolf...")

        BoardGame gameWithMostPlayers = null;

        for (BoardGame game : games) {
            if (gameWithMostPlayers == null) {
                gameWithMostPlayers = game;
            } else if (game.getMaxPlayers() > gameWithMostPlayers.getMaxPlayers()) {
                gameWithMostPlayers = game;
            }
        }

        System.out.println(gameWithMostPlayers.getName());
    }
}

