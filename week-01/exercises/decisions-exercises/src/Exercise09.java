import java.util.Scanner;

public class Exercise09 {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        System.out.print("Enter a minimum value: ");
        int min = console.nextInt();

        System.out.print("Enter a maximum value: ");
        int max = console.nextInt();

        System.out.print("Enter an actual value: ");
        int actual = console.nextInt();

        if (actual >= min && actual <= max) {
            System.out.println("The actual value is valid! It is between the minimum and maximum values.");
        }
        else if (actual < min) {
            System.out.println("The actual value is invalid. It is lower than the minimum value.");
        }
        else if (actual > max) {
            System.out.println("The actual value is invalid. It is higher than the maximum value.");
        }

    }

}
