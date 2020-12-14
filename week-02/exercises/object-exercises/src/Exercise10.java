import java.util.Scanner;

public class Exercise10 {

    public static void main(String[] args) {
        // BALLOON GAME
        Scanner console = new Scanner(System.in);

        // 1. Instantiate three balloons of different colors.

        Balloon[] balloons = {
                new Balloon("red"),
                new Balloon("green"),
                new Balloon("blue"),
        };

        do {
            System.out.print("Inflate? [y/n]: ");
            if (console.nextLine().equalsIgnoreCase("y")) {
                // 2. If the user confirms an inflate, inflate each balloon.

                for (int i = 0; i < balloons.length; i++) {
                    balloons[i].inflate();
                }
            }
            // 3. When one or more balloons explode, stop the loop.
        } while (!balloons[0].isExploded() && !balloons[1].isExploded() && !balloons[2].isExploded());

        // 4. Print the color of the winners (balloons that exploded).
        System.out.println("Winner(s):");
        for (int i = 0; i < balloons.length; i++) {
            if (balloons[i].isExploded()) {
                System.out.println(balloons[i].getColor() + " balloon");
            }
        }
    }
}
