import java.util.Scanner;

public class CapsuleHotel {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        displayIntroduction();
        int numOfCapsules = getNumberOfCapsules(console);
        String[] capsules = new String[numOfCapsules];
        displayMenu(console, capsules);
    }

    public static void displayIntroduction() {
        String title = "Welcome to Capsule-Capsule";
        System.out.println(title);
        System.out.println("=".repeat(title.length()));
    }

    public static int getNumberOfCapsules(Scanner console) {
        int numOfCapsules;
        System.out.print("Enter the number of capsules available: ");
        numOfCapsules = Integer.parseInt(console.nextLine());
        System.out.println();
        System.out.printf("There are %s unoccupied capsules ready to be booked.%n", numOfCapsules);
        System.out.println();
        return numOfCapsules;
    }

    public static void displayMenu(Scanner console, String[] capsules) {
        String menuSelection = "";
        boolean exitProgram = false;

        do {
            menuSelection = getMenuSelection(console);
            System.out.println();
            switch (menuSelection) {
                case "1":
                    checkIn(console, capsules);
                    break;
                case "2":
                    checkOut(console, capsules);
                    break;
                case "3":
                    viewGuests(console, capsules);
                    break;
                case "4":
                    exitProgram = exit(console);
                    break;
                default:
                    System.out.println("I do not recognize that command.");
            }
            System.out.println();
        } while (!exitProgram);
    }

    public static String getMenuSelection(Scanner console) {
        // Print main menu to user
        String title = "Main Menu";
        System.out.println(title);
        System.out.println("=".repeat(title.length()));
        System.out.println("1. Check In");
        System.out.println("2. Check Out");
        System.out.println("3. View Guests");
        System.out.println("4. Exit");

        // Get menu selection from user
        System.out.print("Choose an option [1-4]: ");
        return console.nextLine();
    }

    public static void checkIn(Scanner console, String[] capsules) {
        boolean unoccupiedCapsulesExist;
        String guestName;
        int capsuleNumber;

        unoccupiedCapsulesExist = checkForUnoccupiedCapsules(capsules);
        if (unoccupiedCapsulesExist) {
            // Get Guest Name for check in
            System.out.print("Guest Name: ");
            do {
                guestName = console.nextLine();
            } while (guestName.isBlank());

            // Get Capsule # for check in
            do {
                System.out.printf("Capsule #[1-%d]: ", capsules.length);
                capsuleNumber = Integer.parseInt(console.nextLine());
                System.out.println();
                if (capsuleNumber < 1 || capsuleNumber > capsules.length) {
                    System.out.println("That is not a valid capsule number.");
                } else if (capsules[capsuleNumber-1] != null) {
                    System.out.println("Error :(");
                    System.out.printf("Capsule #%d is occupied by %s.%n", capsuleNumber, capsules[capsuleNumber - 1]);
                    System.out.println();
                }
            } while (capsules[capsuleNumber-1] != null);

            // Update capsule data and confirm
            capsules[capsuleNumber-1] = guestName;
            System.out.println("Success :)");
            System.out.printf("%s is booked in capsule #%d%n", guestName, capsuleNumber);
            System.out.println();
        }
    }

    public static boolean checkForUnoccupiedCapsules(String[] capsules) {
        boolean unoccupiedCapsulesExist;

        // Get number of unoccupied capsules
        int unoccupiedCapsulesCount = 0;

        for (int i = 0; i < capsules.length; i++) {
            if (capsules[i] == null) {
                unoccupiedCapsulesCount++;
            }
        }

        if (unoccupiedCapsulesCount > 0) {
            // Grab capsule numbers of unoccupied capsules
            int[] unoccupiedCapsules = new int[unoccupiedCapsulesCount];
            int nextIndex = 0;
            for (int i = 0; i < capsules.length; i++) {
                if (capsules[i] == null) {
                    unoccupiedCapsules[nextIndex] = i + 1;
                    nextIndex++;
                }
            }
            // Print list of unoccupied capsules
            if (unoccupiedCapsulesCount > 11) { // if list is longer than 11, trim list to first 11 available capsules
                System.out.println("The first 11 unoccupied capsules:");
                for (int i = 0; i < 11; i++) {
                    System.out.printf("%d: %s%n", unoccupiedCapsules[i],
                            capsules[unoccupiedCapsules[i]-1] == null ?
                                    "[unoccupied]" : capsules[unoccupiedCapsules[i]-1]);
                }
            } else { // otherwise, show full list
                System.out.println("The following capsules are unoccupied:");
                for (int i = 0; i < unoccupiedCapsules.length; i++) {
                    System.out.printf("%d: %s%n", unoccupiedCapsules[i],
                            capsules[unoccupiedCapsules[i]-1] == null ?
                                    "[unoccupied]" : capsules[unoccupiedCapsules[i]-1]);
                }
            }
            unoccupiedCapsulesExist = true;
        } else {
            System.out.println("All capsules are currently occupied.");
            unoccupiedCapsulesExist = false;
        }

        return unoccupiedCapsulesExist;
    }

    public static void checkOut(Scanner console, String[] capsules) {
        int capsuleNumber;
        boolean occupiedCapsulesExist;

        String title = "Check Out";
        System.out.println(title);
        System.out.println("=".repeat(title.length()));

        occupiedCapsulesExist = checkForOccupiedCapsules(capsules);
        if (occupiedCapsulesExist) {
            // Grab Capsule # for check out
            do {
                System.out.printf("Capsule #[1-%d]: ", capsules.length);
                capsuleNumber = Integer.parseInt(console.nextLine());
                System.out.println();
                if (capsuleNumber < 1 || capsuleNumber > capsules.length) {
                    System.out.printf("There is no capsule #%d at this hotel.%n", capsuleNumber);
                } else if (capsules[capsuleNumber-1] == null) {
                    System.out.println("Error :(");
                    System.out.printf("Capsule #%d is unoccupied.%n", capsuleNumber);
                    System.out.println();
                }
            } while (capsules[capsuleNumber-1] == null);
            // Delete capsule data and confirm
            System.out.println("Success :)");
            System.out.printf("%s checked out from capsule #%d%n",capsules[capsuleNumber-1], capsuleNumber);
            System.out.println();
            capsules[capsuleNumber-1] = null;
        }
    }

    public static boolean checkForOccupiedCapsules(String[] capsules) {
        boolean occupiedCapsulesExist;

        // Get number of occupied capsules
        int occupiedCapsulesCount = 0;

        for (int i = 0; i < capsules.length; i++) {
            if (capsules[i] != null) {
                occupiedCapsulesCount++;
            }
        }

        if (occupiedCapsulesCount > 0) {
            // Grab capsule numbers of occupied capsules
            int[] occupiedCapsules = new int[occupiedCapsulesCount];
            int nextIndex = 0;
            for (int i = 0; i < capsules.length; i++) {
                if (capsules[i] != null) {
                    occupiedCapsules[nextIndex] = i + 1;
                    nextIndex++;
                }
            }
            // Print list of occupied capsules
            System.out.println("The following capsules are occupied:");
            for (int i = 0; i < occupiedCapsules.length; i++) {
                System.out.printf("%d: %s%n", occupiedCapsules[i], capsules[occupiedCapsules[i]-1]);
            }
            occupiedCapsulesExist = true;
        } else {
            System.out.println("There are no currently occupied capsules.");
            occupiedCapsulesExist = false;
        }

        return occupiedCapsulesExist;
    }

    public static void viewGuests(Scanner console, String[] capsules) {
        int capsuleNumber;

        String title = "View Guests";
        System.out.println(title);
        System.out.println("=".repeat(title.length()));

        // Get Capsule # for guest list generation
        do {
            System.out.printf("Capsule #[1-%d]: ", capsules.length);
            capsuleNumber = Integer.parseInt(console.nextLine());
            System.out.println();
            if (capsuleNumber < 1 || capsuleNumber > capsules.length) {
                System.out.println("That is not a valid capsule number.");
                System.out.println();
            }
        } while (capsuleNumber < 1 || capsuleNumber > capsules.length);

        // Print list of guests, with decision making based on where in array the call is starting
        System.out.println("Capsule: Guest");
        if (capsules.length < 11) { // if less than 11 capsules total, then print all capsules
            printGuestList(1, capsules.length, capsules);
        } else if (capsuleNumber < 6) { // if at lower limit then return first 11 capsules
            printGuestList(1, 11, capsules);
        } else if (capsuleNumber > capsules.length - 5) { // if at upper limit then return last 11 capsules
            printGuestList(capsules.length - 10, capsules.length, capsules);
        } else { // default: print requested capsule and 5 capsules above and below
            printGuestList(capsuleNumber - 5, capsuleNumber + 5, capsules);
        }
    }

    public static void printGuestList(int capsuleLow, int capsuleHigh, String[] capsules) {
        for (int i = capsuleLow - 1; i <= capsuleHigh - 1; i++) {
            System.out.printf("%d: %s%n", i + 1, capsules[i] == null ? "[unoccupied]" : capsules[i]);
        }
        System.out.println();
    }

    public static boolean exit(Scanner console) {
        String exitAnswer = "";
        boolean exitProgram = false;

        String title = "Exit";
        System.out.println(title);
        System.out.println("=".repeat(title.length()));
        System.out.println("Are you sure you want to exit?");
        System.out.println("All data will be lost.");

        // Get user confirmation
        do {
            System.out.print("Exit [y/n]: ");
            exitAnswer = console.nextLine();
            System.out.println();

            if (exitAnswer.equalsIgnoreCase("y")) {
                exitProgram = true;
                System.out.println("Goodbye!");
            } else if (exitAnswer.equalsIgnoreCase("n")) {
                exitProgram = false;
            } else {
                System.out.println("I do not understand.");
                System.out.println();
            }

        } while (!exitAnswer.equalsIgnoreCase("y") && !exitAnswer.equalsIgnoreCase("n"));

        return exitProgram;
    }
}
