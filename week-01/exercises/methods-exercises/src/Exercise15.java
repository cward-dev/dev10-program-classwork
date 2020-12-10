import java.util.Scanner;

public class Exercise15 {
    /* FIZZ BUZZ

    Historically, the Fizz Buzz (https://en.wikipedia.org/wiki/Fizz_buzz) problem was used in programming interviews.
    Not sure if it still is. Just in case, we'll get it out of the way in Milestone 1.

    Write a program to:
    - Prompt a user for a positive integer and store the result. (Could reuse a readInt method.)
    - Loop from the number 1 to the user's integer.
    - If the number is divisible by 3, print Fizz.
    - If the number is divisible by 5, print Buzz.
    - If the number is divisible by both 3 and 5, print Fizz Buzz.
    - If the number is not divisible by either 3 or 5, print the number.

    Example Output:
    1
    2
    Fizz
    4
    Buzz
    Fizz
    7
    8
    Fizz
    Buzz
    11
    Fizz
    13
    14
    Fizz Buzz
    16
    17
    Fizz
     */

    public static void main(String[] args) {
        int value = readInt("Enter a positive integer: ");

        loopToValue(value);
    }

    public static int readInt(String prompt) {
        Scanner console = new Scanner(System.in);
        int value;
        do {
            System.out.print(prompt);
            value = Integer.parseInt(console.nextLine());
        } while (!(value >= 1));

        return value;
    }

    public static boolean divisibleByNum(int value, int divBy) {
        if (value % divBy == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void loopToValue(int value) {
        for (int i = 1; i <= value; i++) {
            boolean divBy3 = divisibleByNum(i, 3);
            boolean divBy5 = divisibleByNum(i, 5);

            if (divBy3 && divBy5) {
                System.out.println("Fizz Buzz");
            } else if (divBy3) {
                System.out.println("Fizz");
            } else if (divBy5) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
    }
}
