import java.util.Scanner;


public class skyrim {

    public static void main(String[] args) {
    Scanner console = new Scanner(System.in);

    String playerName;

    System.out.println("THE ELDER SCROLLS V: SKYRIM");
    System.out.println("-".repeat(50));
    System.out.println("The sound of wheels on gravel wakes you from your slumber. You find yourself on a cart as you regain consciousness.");
    System.out.println("You don't know how you got here, but you are atop a pile of motionless bodies. You lift your head to take in your surroundings.");
    System.out.println("A man in a guard's uniform turns around from his seat at the front of the cart.");
    System.out.println();

    System.out.println("\"Ah, you're awake,\" he says. \"So, who... are you?\"");
    System.out.println();
    System.out.print("?: ");
    playerName = console.nextLine();
    System.out.println();
    System.out.println(playerName + "... That's an odd name. Well, you aren't among the dead today. Off with you.");
    System.out.println();
    System.out.println("The guard walks over and pushes you off the cart. You tumble onto the gravel and watch as the cart plods along into the distance.");
    System.out.println();
    System.out.println("What do you do?");

    chooseCommand();

    }

    public static void chooseCommand() {
        boolean choosingCommand = true;
        Scanner console = new Scanner(System.in);

        do {

            System.out.print("?: ");
            String command = console.nextLine();
            String actionText = "You chose to ";
            System.out.println();

            switch (command) {
                case "move":
                    System.out.println(actionText + "move");
                    break;
                case "inspect":
                    System.out.println(actionText + "inspect");
                    break;
                case "interact":
                    System.out.println(actionText + "interact");
                    break;
                case "respond":
                    System.out.println(actionText + "respond");
                    break;
                case "attack":
                    System.out.println(actionText + "attack");
                    break;
                case "take":
                    System.out.println(actionText + "take");
                    break;
                case "inventory":
                case "bag":
                    System.out.println(actionText + "inventory");
                    break;
                case "exit":
                case "quit":
                    System.out.println(actionText + "quit");
                    break;
                case "help":
                    helpCommand();
                    break;
            }
            System.out.println();
        } while (choosingCommand);
    }

    public static void helpCommand() {
        System.out.println("You may enter the following commands:");
        System.out.println("* move");
        System.out.println("* inspect");
        System.out.println("* interact");
        System.out.println("* respond");
        System.out.println("* attack");
        System.out.println("* take");
        System.out.println("* inventory");
        System.out.println("* exit");
        System.out.println("* help");

    }
}
