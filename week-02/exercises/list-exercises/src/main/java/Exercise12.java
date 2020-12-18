import learn.BoardGame;
import learn.GameRepository;

import java.util.ArrayList;

public class Exercise12 {

    public static void main(String[] args) {

        ArrayList<BoardGame> games = GameRepository.getAll();

        // 1. Shift all games two positions to the left. A game at index 0 "shifts" to the end of the list.
        // Example: A,B,C,D,E -> shifted two positions is -> C,D,E,A,B
        // 2. Print `games` and confirm the new order.

        games.add(games.get(0));
        games.add(games.get(1));

        games.remove(0);
        games.remove(0);

        for (BoardGame game : games) {
            System.out.println(game);
        }
    }
}
