import java.util.Random;
import java.util.Scanner;

public class Game {

    // constants
    private static int WIDTH = 10;
    private final static String WALL_CHARACTER = "M";
    private final static String EMPTY_CHARACTER = ".";

    private final Scanner console = new Scanner(System.in);
    private Hero hero;
    private int treasureCounter;
    private int totalTreasures;
    private GameObject[][] map;

    private boolean isOver;

    public void run() {
        setUp();
        while (!isOver) {
            printWorld();
            move();
        }
        printWorld();
    }

    private void setUp() {
        System.out.print("What is the name of your hero?: ");
        String name = console.nextLine();

        System.out.print("What symbol represents your hero?: ");
        char symbol = console.nextLine().charAt(0);

        do {
            System.out.print("Width of World [5+]: ");
            WIDTH = Integer.parseInt(console.nextLine());
            System.out.println();
        } while (WIDTH < 5);

        map = new GameObject[WIDTH][WIDTH];

        // Make Hero
        Random rand = new Random();
        int x;
        int y;
        do {
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(WIDTH);
        } while (map[x][y] != null);
        hero = new Hero(name, symbol, x, y);

        // Initialize Map Objects
        placeGameObjects("treasure", (int)(WIDTH*0.5), rand);
        placeGameObjects("hiddenItem", (int)(WIDTH*0.75), rand);
        placeGameObjects("trap", (int)(WIDTH*0.75), rand);
    }

    private void placeGameObjects(String type, int amount, Random rand) {
        int x;
        int y;

        for (int i = 0; i < amount; i++) {
            do {
                x = rand.nextInt(WIDTH);
                y = rand.nextInt(WIDTH);
            } while (map[x][y] != null || (x == hero.getX() && y == hero.getY()));

            switch (type) {
                case "treasure":
                    map[x][y] = new Treasure(x, y);
                    totalTreasures = amount;
                    break;
                case "hiddenItem":
                    map[x][y] = new HiddenItem(x, y);
                    break;
                case "trap":
                    map[x][y] = new Trap(x, y);
                    break;
            }
        }
    }

    private void printWorld() {
        System.out.println("Energy: " + hero.getEnergyCount());
        System.out.println("Treasures Found: " + treasureCounter);
        System.out.println();

        // top wall border
        System.out.println((WALL_CHARACTER + " ").repeat(WIDTH + 2));

        for (int row = 0; row < WIDTH; row++) {
            // left wall border
            System.out.print(WALL_CHARACTER);
            System.out.print(" ");
            for (int col = 0; col < WIDTH; col++) {
                if (col == hero.getX() && row == hero.getY() && !isOver) {
                    System.out.print(hero.getSymbol());
                } else if (map[col][row] == null) {
                    System.out.print(EMPTY_CHARACTER);
                } else {
                    System.out.print(map[col][row].getSymbol());
                }
                System.out.print(" ");
            }
            // right wall border
            System.out.println(WALL_CHARACTER);
        }

        // bottom wall border
        System.out.println((WALL_CHARACTER + " ").repeat(WIDTH + 2));

        System.out.println();
    }

    private void move() {

        System.out.println("Press [E] to search location.");
        System.out.print(hero.getName() + ", move [WASD]: ");
        String move = console.nextLine().trim().toUpperCase();
        System.out.println();


        if (move.length() != 1) {
            return;
        }

        switch (move.charAt(0)) {
            case 'W':
                hero.moveUp();
                hero.setEnergyCount(hero.getEnergyCount() - 1);
                break;
            case 'A':
                hero.moveLeft();
                hero.setEnergyCount(hero.getEnergyCount() - 1);
                break;
            case 'S':
                hero.moveDown();
                hero.setEnergyCount(hero.getEnergyCount() - 1);
                break;
            case 'D':
                hero.moveRight();
                hero.setEnergyCount(hero.getEnergyCount() - 1);
                break;
            case 'E':
                searchTile();
                break;
        }

        checkTile(hero, WIDTH, map);

        // Check if energy is depleted
        if (hero.getEnergyCount() <= 0 && !isOver) {
            isOver = true;
            System.out.println(hero.getName() + " has run out of energy! You lose.");
            System.out.println();
        }
    }

    private void checkTile(Hero hero, int WIDTH, GameObject[][] map) {
        if (hero.getX() < 0 || hero.getX() >= WIDTH
                || hero.getY() < 0 || hero.getY() >= WIDTH) {
            isOver = true;
            System.out.println(hero.getName() + " touched lava! You lose.");
        } else if (map[hero.getX()][hero.getY()] instanceof Trap) {
            map[hero.getX()][hero.getY()] = null;
            isOver = true;
            System.out.println(hero.getName() + " stepped on a trap! You lose.");
        } else if (map[hero.getX()][hero.getY()] instanceof Treasure) {
            map[hero.getX()][hero.getY()] = null;
            treasureCounter++;
            System.out.println(hero.getName() + " found a treasure!");
        }

        // Check if player won
        if (treasureCounter >= totalTreasures) {
            System.out.println(hero.getName() + " found all of the treasure! You win!");
            isOver = true;
        }
    }

    private void searchTile() {
        if (map[hero.getX()][hero.getY()] instanceof HiddenItem) {
            map[hero.getX()][hero.getY()] = null;
            hero.setEnergyCount(hero.getEnergyCount() + 5);
            System.out.println("You found food! You feel nourished!");
        } else {
            System.out.println("You search but there is nothing here!");
        }
    }
}