import java.util.Scanner;

public class RockPaperScissors {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        String enemyShoot = "";
        int yourScore = 0;
        int opponentScore = 0;

        for(int i = 0; i < 3; i++) {
            System.out.println("Rock... Paper... Scissors... Shoot!");
            String yourShoot = console.nextLine();
            System.out.println();
            System.out.println("You played " + yourShoot.toLowerCase());

            if (i == 0) {
                enemyShoot = "paper";
            } else if (i == 1) {
                enemyShoot = "scissors";
            } else if (i == 2) {
                enemyShoot = "rock";
            }

            System.out.println("Opponent played " + enemyShoot);

            switch (yourShoot) {
                case "rock":
                    if (enemyShoot == "rock") {
                        System.out.println("You tie! Try again!");
                    } else if (enemyShoot == "paper") {
                        System.out.println("Opponent won!");
                        opponentScore++;
                    } else if (enemyShoot == "scissors") {
                        System.out.println("You won!");
                        yourScore++;
                    }
                    break;
                case "paper":
                    if (enemyShoot == "paper") {
                        System.out.println("You tie! Try again!");
                    } else if (enemyShoot == "scissors") {
                        System.out.println("Opponent won!");
                        opponentScore++;
                    } else if (enemyShoot == "rock") {
                        System.out.println("You won!");
                        yourScore++;
                    }
                    break;
                case "scissors":
                    if (enemyShoot == "scissors") {
                        System.out.println("You tie! Try again!");
                    } else if (enemyShoot == "rock") {
                        System.out.println("Opponent won!");
                        opponentScore++;
                    } else if (enemyShoot == "paper") {
                        System.out.println("You won!");
                        yourScore++;
                    }
                    break;

            }

            System.out.println("Your Score: " + yourScore + " | Opponent Score: " + opponentScore);
            System.out.println();

        }

        System.out.println("The final score is...");
        System.out.println("Your Score: " + yourScore + " | Opponent Score: " + opponentScore);
        System.out.println("Thanks for playing!");

    }

}
