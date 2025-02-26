import learn.BoardGame;
import learn.GameRepository;

import java.util.ArrayList;

public class Exercise03 {

    public static void main(String[] args) {

        ArrayList<BoardGame> games = GameRepository.getAll();

        // 1. Add three new games to `games` with the `add` method.
        // 2. Print `games` after each add.

        games.add(new BoardGame("Clank!", 2, 4, "Deck Building"));
        System.out.println(games);
        games.add(new BoardGame("Root", 1, 6, "War"));
        System.out.println(games);
        games.add(new BoardGame("Spirit Island", 1, 4, "Cooperative Strategy"));
        System.out.println(games);
    }
}
