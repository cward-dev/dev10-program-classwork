package learn.gomoku.players;

import learn.gomoku.game.Gomoku;
import learn.gomoku.game.Stone;

import java.util.List;
import java.util.Random;

public class SmarterPlayer implements Player {

    private static String[] titles = {"Dr.", "Professor", "Chief Exec", "Specialist", "The Honorable",
            "Prince", "Princess", "The Venerable", "The Eminent"};
    private static String[] names = {
            "Evelyn", "Wyatan", "Jud", "Danella", "Sarah", "Johnna",
            "Vicki", "Alano", "Trever", "Delphine", "Sigismundo",
            "Shermie", "Filide", "Daniella", "Annmarie", "Bartram",
            "Pennie", "Rafael", "Celine", "Kacey", "Saree", "Tu",
            "Erny", "Evonne", "Charita", "Anny", "Mavra", "Fredek",
            "Silvio", "Cam", "Hulda", "Nanice", "Iolanthe", "Brucie",
            "Kara", "Paco"};
    private static String[] lastNames = {"Itch", "Potato", "Mushroom", "Grape", "Mouse", "Feet",
            "Nerves", "Sweat", "Sweet", "Bug", "Piles", "Trumpet", "Shark", "Grouper", "Flutes", "Showers",
            "Humbug", "Cauliflower", "Shoes", "Hopeless", "Zombie", "Monster", "Fuzzy"};

    private final Random random = new Random();
    private final String name;
    private int futureRow = 0;
    private int futureColumn = 0;

    public SmarterPlayer() {
        name = String.format("%s %s %s",
                titles[random.nextInt(titles.length)],
                names[random.nextInt(names.length)],
                lastNames[random.nextInt(lastNames.length)]
        );
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Stone generateMove(List<Stone> previousMoves) {
        boolean isBlack = true;
        Stone lastMove = null;
        Stone nextStone = null;

        if (previousMoves == null || previousMoves.isEmpty()) {
            nextStone = generateRandomMove(previousMoves);
            futureRow = nextStone.getRow() - 1;
            futureColumn = nextStone.getColumn();
            return nextStone;
        }

        lastMove = previousMoves.get(previousMoves.size() - 1);
        isBlack = !lastMove.isBlack();

        if (lastMove.getRow() == futureRow && lastMove.getColumn() == futureColumn) {
            nextStone = generateRandomMove(previousMoves);
            futureRow = nextStone.getRow() - 1;
            futureColumn = nextStone.getColumn();
            return nextStone;
        }

        if (futureRow < 0) {
            nextStone = generateRandomMove(previousMoves);
            futureRow = nextStone.getRow() - 1;
            futureColumn = nextStone.getColumn();
            return nextStone;
        }

        nextStone = new Stone(futureRow, futureColumn, isBlack);
        futureRow = nextStone.getRow() - 1;
        futureColumn = nextStone.getColumn();

        return nextStone;

    }

    public Stone generateRandomMove(List<Stone> previousMoves) {
        Stone nextStone;
        boolean isBlack = true;
        if (previousMoves != null && !previousMoves.isEmpty()) {
            Stone lastMove = previousMoves.get(previousMoves.size() - 1);
            isBlack = !lastMove.isBlack();
        }
        do {
            nextStone = new Stone(
                    random.nextInt(Gomoku.WIDTH),
                    random.nextInt(Gomoku.WIDTH),
                    isBlack);
        } while (nextStone.getRow() < 4);
        return nextStone;
    }
}
