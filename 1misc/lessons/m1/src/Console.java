import java.sql.SQLOutput;
import java.util.Scanner;

public class Console {

    public static void main(String[] args) {

        Scanner console = new Scanner(System.in); // 1
        // Header
        String rowFormat = "| %-20s | %-15s | %-6s | %n";
        // Body
        System.out.println("Beetle Families");
        System.out.println("-".repeat(51));
        System.out.printf(rowFormat, "Common Name", "Latin Name", "Rating");
        System.out.println("-".repeat(51));
        System.out.printf(rowFormat, "Carrion Beetles", "Silphidae", 9.7);
        System.out.printf(rowFormat, "Darkling Beetles", "Tenebrionidae", 10.0);
        System.out.printf(rowFormat, "Fireflies", "Lampyridae", 9.98);
        System.out.printf(rowFormat, "Fungus Weevils", "Anthribidae", 9.45);
        System.out.printf(rowFormat, "Ladybugs", "Coccinellidae", 9.62);
        // Footer
        System.out.println("-".repeat(51));


    }
}