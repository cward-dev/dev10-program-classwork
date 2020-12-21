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
    private int direction = 0;

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
        Stone lastMove;
        Stone nextStone;

        if (previousMoves == null || previousMoves.isEmpty()) {
            nextStone = generateRandomMove(isBlack);
            generateFutureRowAndColumn(nextStone);
            return nextStone;
        }

        lastMove = previousMoves.get(previousMoves.size() - 1);
        isBlack = !lastMove.isBlack();

        if ((lastMove.getRow() == futureRow && lastMove.getColumn() == futureColumn) ||
                futureRow < 0 || futureRow > Gomoku.WIDTH - 1
                || futureColumn < 0 || futureColumn > Gomoku.WIDTH - 1) {
            nextStone = generateRandomMove(isBlack);
            generateFutureRowAndColumn(nextStone);
            return nextStone;
        }

        nextStone = new Stone(futureRow, futureColumn, isBlack);
        generateFutureRowAndColumn(nextStone);

        return nextStone;
    }

    public void generateFutureRowAndColumn(Stone nextStone) {
        switch (direction) {
            case 0: // down
                futureRow = nextStone.getRow() + 1;
                futureColumn = nextStone.getColumn();
                break;
            case 1: // down right
                futureRow = nextStone.getRow() + 1;
                futureColumn = nextStone.getColumn() + 1;
                break;
            case 2: // right
                futureRow = nextStone.getRow();
                futureColumn = nextStone.getColumn() + 1;
                break;
            case 3: // up right
                futureRow = nextStone.getRow() - 1;
                futureColumn = nextStone.getColumn() + 1;
                break;
            case 4: // up
                futureRow = nextStone.getRow() - 1;
                futureColumn = nextStone.getColumn();
                break;
            case 5: // up left
                futureRow = nextStone.getRow() - 1;
                futureColumn = nextStone.getColumn() - 1;
                break;
            case 6: // left
                futureRow = nextStone.getRow();
                futureColumn = nextStone.getColumn() - 1;
                break;
            case 7: // down left
                futureRow = nextStone.getRow() + 1;
                futureColumn = nextStone.getColumn() - 1;
                break;
        }
    }

    public Stone generateRandomMove(boolean isBlack) {
        Stone nextStone;
        direction = random.nextInt(7);
        int leftBound = 0; // 4
        int rightBound = Gomoku.WIDTH - 5; // G
        int downBound = Gomoku.WIDTH - 5; // G
        int upBound = 0; // 4

        switch (direction) {
            case 0: // down
                downBound = Gomoku.WIDTH - 5;
                break;
            case 1: // down right
                downBound = Gomoku.WIDTH - 5;
                rightBound = Gomoku.WIDTH - 5;
                break;
            case 2: // right
                rightBound = Gomoku.WIDTH - 5;
                break;
            case 3: // up right
                upBound = 4;
                rightBound = Gomoku.WIDTH - 5;
                break;
            case 4: // up
                upBound = 4;
                break;
            case 5: // up left
                upBound = 4;
                leftBound = 4;
                break;
            case 6: // left
                leftBound = Gomoku.WIDTH - 5;
                break;
            case 7: // down left
                downBound = Gomoku.WIDTH - 5;
                leftBound = 4;
                break;
        }

        do {
            nextStone = new Stone(
                    random.nextInt(Gomoku.WIDTH),
                    random.nextInt(Gomoku.WIDTH),
                    isBlack);
        } while (nextStone.getRow() > downBound || nextStone.getRow() < upBound ||
                 nextStone.getColumn() > rightBound || nextStone.getColumn() < leftBound);
        return nextStone;
    }
}
