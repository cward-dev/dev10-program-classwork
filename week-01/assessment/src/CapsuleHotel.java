import java.util.Scanner;

public class CapsuleHotel {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        introduction();
        int numOfCapsules = getNumberOfCapsules(console);
        String[] capsules = new String[numOfCapsules];
        menu(console, capsules);
    }

    public static void introduction() {
        String message = "Welcome to Capsule-Capsule";
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }

    public static int getNumberOfCapsules(Scanner console) {
        int numOfCapsules;
    }

    public static void menu(Scanner console, String[] capsules) {

    }

    public static void checkIn(Scanner console, String[] capsules) {

    }

    public static void checkOut(Scanner console, String[] capsules) {

    }

    public static void viewGuests(Scanner console, String[] capsules) {

    }

    public static void exit(Scanner console) {

    }

}
