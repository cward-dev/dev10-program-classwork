import java.util.Scanner;

public class Inventory {

    public static void main(String[] args) {
        // show intro
        System.out.println("Welcome to RPG Inventory");
        System.out.println();

        String[] backpack = new String[8];
        Scanner console = new Scanner(System.in);

        // loop menu
        String input;
        do {
            System.out.println("Main Menu");
            System.out.println("-".repeat(20));
            System.out.println("1. Add an Item");
            System.out.println("2. Drop an Item");
            System.out.println("3. Display Backpack");
            System.out.println("4. Exit");
            System.out.println();

            System.out.print("Select [1-3]: ");
            input = console.nextLine();
            System.out.println();

            switch (input) {
                case "1":
                    addItem(console, backpack);
                    break;
                case "2":
                    dropItem(console, backpack);
                    break;
                case "3":
                    displayBackpack(backpack);
                    break;
                case "4":
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("I don't understand that option.");
                    break;
            }
            System.out.println();
        } while (!input.equals("4"));

    }

    static void addItem(Scanner console, String[] backpack){
        System.out.println("Add an Item");
        System.out.println("-".repeat(20));
        System.out.print("Slot #: ");
        int slot = Integer.parseInt(console.nextLine()) - 1;
        if(slot < 0 || slot >= backpack.length){
            return;
        }
        System.out.print("Item: ");
        String item = console.nextLine();
        backpack[slot] = item;
    }

    static void dropItem(Scanner console, String[] backpack){
        System.out.println("Drop an Item");
        System.out.println("-".repeat(20));
        System.out.print("Slot #: ");
        int slot = Integer.parseInt(console.nextLine()) - 1;
        if(slot < 0 || slot >= backpack.length){
            return;
        }
        System.out.printf("You drop: %s%n", backpack[slot]);
        backpack[slot] = null;
    }

    static void displayBackpack(String[] backpack) {
        System.out.println("Items");
        System.out.println("-".repeat(20));
        for (int i = 0; i < backpack.length; i++) {
            System.out.printf("Slot #%s: %s%n",
                    i + 1, backpack[i] == null ? "[empty]" : backpack[i]);
        }
    }
}
