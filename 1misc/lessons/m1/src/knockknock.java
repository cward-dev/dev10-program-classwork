import java.util.Scanner;

public class knockknock {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("Knock knock");
        String input = console.nextLine();

        if(input.equalsIgnoreCase("Who's there?")){
            System.out.println("Who?");
        } else if (input.equalsIgnoreCase("Whos there")) {
            System.out.println("Who?");
        }

        input = console.nextLine();

        if(input.equalsIgnoreCase("Who who")){
            System.out.println("What are you? An owl?");
        } else if (input.equalsIgnoreCase("Who who")) {
            System.out.println("What are you? An owl?");
        }

        System.out.println("lol");

    }

}