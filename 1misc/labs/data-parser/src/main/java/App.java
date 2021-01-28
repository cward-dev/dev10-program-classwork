import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        boolean cont = true;

        do {
            System.out.print("Enter something resembling a date: ");
            String value = console.nextLine();
            String[] date = parseIt(value);
            System.out.println();
        } while (cont);

    }

    public static String[] parseIt(String value) {
        Parser parser = new Parser();

        List<DateGroup> groups = parser.parse(value);

        List<Date> dates = new ArrayList<>();

        for(DateGroup group:groups) {
            dates = group.getDates();
        }

        String[] date;
        try {
            date = dates.get(0).toString().split(" ");
            System.out.printf("%s %s, %s%n", date[1], date[2], date[5]);
        } catch (IndexOutOfBoundsException ex) {
            date = null;
            System.out.println("Date not found.");
        }

        return date;
    }

}
