import java.util.Scanner;

public class Dice {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        int score = -1;
        int failCount = 0;
        int partialCount = 0;
        int successCount = 0;
        int numOfRolls = 1000000;

        do {
            System.out.print("Score [0+]: ");
            try {
                score = Integer.parseInt(console.nextLine());
            } catch (Exception e) {
                System.out.println("I do not recognize that. Please enter a number greater than 0.");
            }
        } while (score < 0);

        for (int i = 0; i < numOfRolls; i++) {
            switch (roll(console, score)) {
                case 0:
                    failCount++;
                    break;
                case 1:
                    partialCount++;
                    break;
                case 2:
                    successCount++;
                    break;
            }
        }

        System.out.println();
        System.out.println("                   Failures: " + (double)failCount/10000);
        System.out.println("Successes with Complication: " + (double)partialCount/10000);
        System.out.println("                  Successes: " + (double)successCount/10000);

    }

    public static int roll(Scanner console, int score) {
        int highDie = 0;
        int successLevel = 0;

        int[] dice = new int[score];

        for (int i = 0; i < dice.length; i++) {
            dice[i] = (int)(Math.random() * 6) + 1;
            if (dice[i] >= highDie) {
                highDie = dice[i];
            }
        }

        if (highDie == 6) { // success
            successLevel = 2;
        } else if (highDie >= 4) { // success with complication
            successLevel = 1;
        } else { // failure
            successLevel = 0;
        }

        return successLevel;
    }

}
