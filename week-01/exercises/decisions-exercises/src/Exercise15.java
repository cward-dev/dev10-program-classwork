import java.util.Locale;
import java.util.Scanner;

public class Exercise15 {

    public static void main(String[] args) {
        // SWITCH OPPOSITES
        // Given a word, print its opposite using a switch statement.
        Scanner console = new Scanner(System.in);

        System.out.print("Enter a word: ");
        String word = console.nextLine().toLowerCase();
        String opposite = null;

        // 1. Re-implement Exercise08 using a switch statement.
        switch (word) {
            case "high":
                opposite = "low";
                break;
            case "cold":
                opposite = "hot";
                break;
            case "little":
                opposite = "big";
                break;
            case "tall":
                opposite = "short";
                break;
            case "wide":
                opposite = "narrow";
                break;
            default:
                break;
        }


        if (opposite == null) {
            System.out.printf("I don't have an opposite for %s.%n", word);
        } else {
            System.out.printf("The opposite of %s is %s.%n", word, opposite);
        }

    }
}
