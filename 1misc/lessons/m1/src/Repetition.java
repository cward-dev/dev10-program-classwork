import java.util.Scanner;

public class Repetition {

    public static void main(String[] args) {

        String message = "Hello from Mars.";
        int index = 0;

        while (index < message.length()) {
            System.out.println(message.charAt(index));
            index++;
        }

        System.out.println();
        index = 0;

        do {
            System.out.println(message.charAt(index));
            index++;
        }
        while (index < message.length());

        Scanner console = new Scanner(System.in);
        System.out.print("Guess a fruit: ");
        for (String input = console.nextLine(); !input.equals("persimmon"); input = console.nextLine()) {
            System.out.printf("%s isn't correct.%nGuess a fruit: ", input);
        }
        System.out.println("You got it!");

        String input;
        while (true) {
            System.out.print("Enter 1-5: ");
            input = console.nextLine();
            if (input.length() == 1 && input.charAt(0) >= '1' && input.charAt(0) <= '5') {
                break;
            }
        }
        System.out.println("Value: " + input);
    }
}
