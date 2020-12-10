import java.util.Scanner;

public class Exercise16 {

    public static void main(String[] args) {
        // 1. Display the following menu and collect an integer choice from the user.
        // (See Exercise14 for a menu example.)
        //
        // Menu
        // 1. Print the name of an animal.
        // 2. Print the name of a state.
        // 3. Print the name of a beetle.
        // 4. Print the name of a mineral.
        // Select [1-4]:
        //
        // 2. Use a switch to cover cases 1-4 as well as a default.
        // For 1-4, print an animal, state, beetle, or mineral respectively.
        // For the default case, print "Unknown Menu Option".
        Scanner console = new Scanner(System.in);

        System.out.println("Menu");
        System.out.println("1. Print the name of an animal.");
        System.out.println("2. Print the name of a state");
        System.out.println("3. Print the name of a beetle.");
        System.out.println("4. Print the name of a mineral.");
        System.out.print("Select [1-4]: ");

        int command = Integer.parseInt(console.nextLine());

        switch (command) {
            case 1:
                System.out.println("Jackal");
                break;
            case 2:
                System.out.println("New Mexico");
                break;
            case 3:
                System.out.println("Dung Beetle");
                break;
            case 4:
                System.out.println("Quartz");
                break;
            default:
                System.out.println("Unknown Menu Option");
                break;
        }

    }
}
