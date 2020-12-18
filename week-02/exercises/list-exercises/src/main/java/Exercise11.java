import learn.BoardGame;
import learn.GameRepository;

import java.util.ArrayList;

public class Exercise11 {

    public static void main(String[] args) {

        ArrayList<BoardGame> games = GameRepository.getAll();

        // 1. Swap the 2nd game with the 4th and the 3rd with the 7th.
        // 2. Print `games` and confirm their order.

        BoardGame secondGame = games.get(1);
        BoardGame thirdGame = games.get(2);
        BoardGame fourthGame = games.get(3);
        BoardGame seventhGame = games.get(6);

        games.remove(secondGame);
        games.remove(fourthGame);
        games.remove(thirdGame);
        games.remove(seventhGame);

        games.add(1, fourthGame);
        games.add(2, seventhGame);
        games.add(3, secondGame);
        games.add(6, fourthGame);

        for (BoardGame game : games) {
            System.out.println(game);
        }
    }
}
