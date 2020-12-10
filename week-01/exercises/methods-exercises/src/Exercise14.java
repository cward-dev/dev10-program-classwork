import java.util.Scanner;

public class Exercise14 {
    /* SHORT SURVEY

    Write a program that asks a user four questions and prints the results:
    - What is your first name?
    - What is your last name?
    - How many towns/cities have you lived in?
    - How many musical instruments can you play?

    Store each answer in a variable with an appropriate type.
    Print the results after the user has answered all four questions.

    Use methods to break the program into reusable blocks of code.
     */

    public static void main(String[] args) {
        String firstName = getAnswer("What is your first name?: ");
        String lastName = getAnswer("What is your last name?: ");
        int numTowns = Integer.parseInt(getAnswer("How many towns/cities have you lived in?"));
        int numInstruments = Integer.parseInt(getAnswer("How many musical instruments can you play?"));

        printSummary(firstName, lastName, numTowns, numInstruments);
    }

    public static String getAnswer(String prompt) {
        System.out.println(prompt);
        Scanner console = new Scanner(System.in);
        return console.nextLine();
    }

    public static void printSummary(String firstName, String lastName, int numTowns, int numInstruments) {
        System.out.println();
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Number of towns/cities lived in: " + numTowns);
        System.out.println("Number of musical instruments played: " + numInstruments);
    }
}
