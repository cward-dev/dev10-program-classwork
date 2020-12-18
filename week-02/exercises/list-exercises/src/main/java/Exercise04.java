import learn.BoardGame;
import learn.GameRepository;

import java.util.ArrayList;

public class Exercise04 {

    public static void main(String[] args) {

        ArrayList<BoardGame> games = GameRepository.getAll();

        // 1. Instantiate a new ArrayList<BoardGame>.
        // 2. Add three BoardGames to the new list.
        // 3. Print the new list.
        // 4. Add items in the new list to `games` with the `addAll` method.
        // 5. Print `games`.

        ArrayList<BoardGame> newGames = new ArrayList<>();
        newGames.add(new BoardGame("Clank!", 2, 4, "Deck Building"));
        newGames.add(new BoardGame("Root", 1, 6, "War"));
        newGames.add(new BoardGame("Spirit Island", 1, 4, "Cooperative Strategy"));
        System.out.println(newGames);

        games.addAll(newGames);
        System.out.println(games);
    }
}
