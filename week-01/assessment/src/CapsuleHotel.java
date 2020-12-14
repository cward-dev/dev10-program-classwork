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
        do {
            System.out.print("Enter the number of capsules available [1-100]: ");
            numOfCapsules = Integer.parseInt(console.nextLine());
            System.out.println();
            if (numOfCapsules < 1 || numOfCapsules > 100) {
                System.out.println("That is not a valid number.");
            }
        } while (numOfCapsules < 1 || numOfCapsules > 100);
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
                    exitProgram = exitProgram(console);
                    break;
                case "autopop":
                    populateHotel(capsules);
                    break;
                case "depop":
                    depopulateHotel(capsules);
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

        String title = "Check In";
        System.out.println(title);
        System.out.println("=".repeat(title.length()));

        unoccupiedCapsulesExist = checkForUnoccupiedCapsules(capsules);
        if (unoccupiedCapsulesExist) {
            // Get Guest Name for check in
            System.out.println("Enter [-1] to return to main menu.");
            System.out.print("Guest Name: ");
            do {
                guestName = console.nextLine();
            } while (guestName.isBlank());

            if (guestName.equalsIgnoreCase("-1")) {
                return;
            }

            // Get Capsule # for check in
            do {
                System.out.printf("Capsule #[1-%d]: ", capsules.length);
                capsuleNumber = Integer.parseInt(console.nextLine());
                System.out.println();
                if (capsuleNumber == -1) { // Return to main menu
                    return;
                } else if (capsuleNumber < 1 || capsuleNumber > capsules.length) {
                    System.out.println("That is not a valid capsule number.");
                } else if (capsules[capsuleNumber-1] != null) {
                    System.out.println("Error :(");
                    System.out.printf("Capsule #%d is occupied by %s.%n", capsuleNumber, capsules[capsuleNumber - 1]);
                    System.out.println();
                }
            } while (capsules[capsuleNumber-1] != null);

            // Update capsule data and confirm
            capsules[capsuleNumber - 1] = guestName;
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

        // Print unoccupied capsules
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
            System.out.println("The following are the first 11 unoccupied capsules.");
            System.out.println();
            printGuestList(1, capsules, unoccupiedCapsules);
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
                System.out.println("Enter [-1] to return to main menu.");
                System.out.printf("Capsule #[1-%d]: ", capsules.length);
                capsuleNumber = Integer.parseInt(console.nextLine());
                System.out.println();
                if (capsuleNumber == -1) { // Return to main menu
                    return;
                } else if (capsuleNumber < 1 || capsuleNumber > capsules.length) { // Error invalid number
                    System.out.printf("There is no capsule #%d at this hotel.%n", capsuleNumber);
                } else if (capsules[capsuleNumber-1] == null) { // Error occupied capsule
                    System.out.println("Error :(");
                    System.out.printf("Capsule #%d is unoccupied.%n", capsuleNumber);
                    System.out.println();
                }
            } while (capsules[capsuleNumber-1] == null);
            // Delete capsule data and confirm
            System.out.println("Success :)");
            System.out.printf("%s checked out from capsule #%d%n",capsules[capsuleNumber-1], capsuleNumber);
            System.out.println();
            capsules[capsuleNumber - 1] = null;
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

        // Print occupied capsules
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
            System.out.println("The following are the first 11 occupied capsules.");
            System.out.println();
            printGuestList(1, capsules, occupiedCapsules);
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
            System.out.println("Enter [-1] to return to main menu.");
            System.out.println("Enter [0] to view the full current guest list.");
            System.out.printf("Capsule #[1-%d]: ", capsules.length);
            capsuleNumber = Integer.parseInt(console.nextLine());
            System.out.println();
            if (capsuleNumber == -1) { // Return to main menu
                return;
            } else if (capsuleNumber == 0) {
                String fullGuestListTitle = "Full Guest List";
                System.out.println(fullGuestListTitle);
                System.out.println("=".repeat(fullGuestListTitle.length()));
                printGuestList(0, capsules);
                return;
            } else if (capsuleNumber < 1 || capsuleNumber > capsules.length) {
                System.out.println("That is not a valid capsule number.");
                System.out.println();
            }
        } while (capsuleNumber < 1 || capsuleNumber > capsules.length);

        printGuestList(capsuleNumber, capsules);
    }

    public static boolean exitProgram(Scanner console) {
        String exitAnswer = "";

        String title = "Exit";
        System.out.println(title);
        System.out.println("=".repeat(title.length()));
        System.out.println("Are you sure you want to exit?");
        System.out.println("All data will be lost.");

        // Get user confirmation
        System.out.print("Exit [y/n]: ");
        exitAnswer = console.nextLine();
        System.out.println();

        if (exitAnswer.equalsIgnoreCase("y")) {
            System.out.println("Goodbye!");
            return true;
        } else {
            if (!exitAnswer.equalsIgnoreCase("n")) {
                System.out.println("I do not understand.");
                System.out.println();
            }
            return false;
        }
    }

    public static void printGuest(int capsuleNumber, String guestName) {
        System.out.printf("%7d: %s%n", capsuleNumber, guestName);
    }

    // Print list of guests (String Array passed in)
    public static void printGuestList(int capsuleNumber, String[] capsules) {
        int capsuleLow;
        int capsuleHigh;

        if (capsules.length < 12 || capsuleNumber == 0) { // if less than 12 total capsules, or user enters 0, print all capsules
            capsuleLow = 1;
            capsuleHigh = capsules.length;
        } else if (capsuleNumber < 6) { // else if below 6 then print first 11
            capsuleLow = 1;
            capsuleHigh = 11;
        } else if (capsuleNumber > capsules.length - 5) { // else if above (capsules.length - 5) then print last 11 capsules
            capsuleLow = capsules.length - 10;
            capsuleHigh = capsules.length;
        } else { // print requested capsule and 5 above/below
            capsuleLow = capsuleNumber - 5;
            capsuleHigh = capsuleNumber + 5;
        }

        System.out.printf("%7s: %s%n", "Capsule", "Guest");
        for (int i = capsuleLow - 1; i <= capsuleHigh - 1; i++) {
            printGuest(i + 1, capsules[i] == null ? "[unoccupied]" : capsules[i]);
        }
        System.out.println();
    }

    // Print list of guests, pulling only occupied or unoccupied rooms (Int Array passed in)
    public static void printGuestList(int capsuleNumber, String[] capsules, int[] capsulesPulled) {
        int capsuleLow;
        int capsuleHigh;

        if (capsulesPulled.length < 12 || capsuleNumber == 0) { // if less than 11 total capsules, or user enters 0, print all capsules
            capsuleLow = 1;
            capsuleHigh = capsulesPulled.length;
        } else if (capsuleNumber < 6) { // else if below 6 then print first 11
            capsuleLow = 1;
            capsuleHigh = 11;
        } else if (capsuleNumber > capsulesPulled.length - 5) { // else if above (capsules.length - 5) then print last 11 capsules
            capsuleLow = capsulesPulled.length - 10;
            capsuleHigh = capsulesPulled.length;
        } else { // print requested capsule and 5 above/below
            capsuleLow = capsuleNumber - 5;
            capsuleHigh = capsuleNumber + 5;
        }

        System.out.printf("%7s: %s%n", "Capsule", "Guest");
        for (int i = capsuleLow - 1; i <= capsuleHigh - 1; i++) {
            printGuest(capsulesPulled[i],
                    capsules[capsulesPulled[i]-1] == null ? "[unoccupied]" : capsules[capsulesPulled[i]-1]);
        }
        System.out.println();
    }

    // Populate all empty hotel rooms with randomly generated names **Test Purposes**
    public static void populateHotel(String[] capsules) {
        int firstNum;
        int lastNum;
        String[] firstName = { "Thomas", "Zachary", "Philip", "Theodore", "Peter", "Katelyn", "Rebecca", "Sarah", "Penelope", "Samantha" };
        String[] lastName = { "Hancock", "Hamilton", "Washington", "Burr", "Jefferson", "Franklin", "Adams", "Madison", "Brown", "Baker" };

        for (int i = 0; i < capsules.length; i++) {
            if (capsules[i] == null) {
                firstNum = (int)(Math.random() * 10);
                lastNum = (int)(Math.random() * 10);
                capsules[i] = firstName[firstNum] + " " + lastName[lastNum];
            }
        }
    }

    // Check out all hotel guests **Test Purposes**
    public static void depopulateHotel(String[] capsules) {
        System.out.printf("Check out all guests[y/n]: ");

        for (int i = 0; i < capsules.length; i++) {
            capsules[i] = null;
        }
    }
}
