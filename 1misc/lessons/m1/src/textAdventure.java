import java.util.Scanner;

public class textAdventure {

    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);

        String location = "main";
        String command;
        boolean exitGame = false;
        boolean foundKey = false;
        String kickBall = "";
        boolean secretDoorOpen = false;

        System.out.print("You are in the " + location + " room. ");

        do {
            System.out.println("Which direction would you like to go?");
            command = console.nextLine();

            switch (command.toLowerCase()) {
                case "north":
                    if (location == "north") {
                        System.out.println("You cannot go further " + command + ".");
                    } else if (location == "south") {
                        location = "main";
                        System.out.println("You are now in the " + location + " room.");

                    } else if (location == "east") {

                        System.out.println("You cannot go further " + command + ".");
                    } else if (location == "west") {

                        System.out.println("You cannot go further " + command + ".");
                    } else if (location == "main") {
                        location = "north";
                        exitGame = true;
                        System.out.println("You are now in the " + location + " room.\nUpon entering you have activated the deadly death trap.\nYou die. And you lose. Game over.");

                    }
                    break;
                case "south":
                    if (location == "south") {
                        if (secretDoorOpen  && foundKey) {
                            System.out.println("You go south of the south room and find a south-er room.\nThis room contains a door with a lock, but luckily you have a key! You leave the house and win!");
                        } else {
                            System.out.println("You cannot go further " + command + ".");
                        }
                    } else if (location == "east") {
                        System.out.println("You cannot go further " + command + ".");
                    } else if (location == "north") {
                        location = "main";
                        System.out.println("You are now in the " + location + " room.");
                    } else if (location == "west") {

                        System.out.println("You cannot go further " + command + ".");
                    }

                    else if (location == "main") {
                        location = "south";
                        System.out.println("You are now in the " + location + " room.");

                    }
                    break;

                case "east":
                    if (location == "north") {
                        System.out.println("You cannot go further " + command + ".");
                    } else if (location == "south") {

                        System.out.println("You cannot go further " + command + ".");
                    } else if (location == "east") {

                        System.out.println("You cannot go further " + command + ".");
                    } else if (location == "west") {
                        location = "main";
                        System.out.println("You are now in the " + location + " room.");

                    } else if (location == "main") {
                        location = "east";
                        System.out.println("You are now in the " + location + " room.");
                        System.out.println("You find a ball on the floor of this room. Would you like to kick it?");
                        kickBall = console.nextLine();
                        if (kickBall.equalsIgnoreCase("yes")) {
                            System.out.println("You have kicked the ball. You get a weird feeling.");
                            secretDoorOpen = true;
                        } else {
                            System.out.println("You choose not to kick the ball.");
                        }

                    }
                    break;

                case "west":
                    if (location == "north") {
                        System.out.println("You cannot go further " + command + ".");
                    } else if (location == "south") {
                        System.out.println("You cannot go further " + command + ".");
                    } else if (location == "east") {
                        location = "main";
                        System.out.println("You are now in the " + location + " room.");
                    } else if (location == "west") {
                        System.out.println("You cannot go further " + command + ".");
                    } else if (location == "main") {
                        location = "west";
                        if (!foundKey) {
                            foundKey = true;
                            System.out.println("You are now in the " + location + " room. You find a fancy key on the floor.");
                        } else {
                            System.out.println("You are now in the " + location + " room.");
                        }
                    }
                    break;

                case "exit":
                    exitGame = true;

                default:
                    System.out.println("That is not a valid input. Please try again.");
            }
        } while (exitGame == false);


    }
}