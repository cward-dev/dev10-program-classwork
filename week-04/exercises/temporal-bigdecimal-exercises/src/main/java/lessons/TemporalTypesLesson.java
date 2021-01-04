package lessons;

// import java.time classes
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class TemporalTypesLesson {

    public static void main(String[] args) {
        // Date
        Random rand = new Random();
        int year = LocalDate.now().getYear();
        int dayOfMonth = rand.nextInt(Month.AUGUST.maxLength()) + 1;

        LocalDate day = LocalDate.of(year, Month.AUGUST, dayOfMonth);
        LocalDate stop = day.plusWeeks(2);

        for (; day.compareTo(stop) < 0; day = day.plusDays(1)) {
            if (day.getDayOfWeek() != DayOfWeek.SATURDAY && day.getDayOfWeek() != DayOfWeek.SUNDAY) {
                System.out.printf("%s: %s%n", day.getDayOfWeek(), day);
            }
        }

        System.out.printf("%n".repeat(3));

        // Time
        final LocalTime noon = LocalTime.of(12, 0);
        final LocalTime quittingTime = LocalTime.of(17, 30);

        Duration appointmentDuration = Duration.ofMinutes(20);
        LocalTime start = LocalTime.of(7, 30);
        LocalTime end = start.plus(appointmentDuration);

        System.out.println("Appointments before lunch:");
        while (!end.isAfter(noon)) {
            System.out.printf("%s - %s%n", start, end);
            start = end;
            end = start.plus(appointmentDuration);
        }

        start = LocalTime.of(13, 0); // 1:00PM
        end = start.plus(appointmentDuration);

        System.out.println();
        System.out.println("Appointments after lunch:");
        while (!end.isAfter(quittingTime)) {
            System.out.printf("%s - %s%n", start, end);
            start = end;
            end = start.plus(appointmentDuration);
        }

        System.out.printf("%n".repeat(3));

        LocalDate now = LocalDate.now();
        Period difference = Period.between(now, now.plusDays(7));
        System.out.println(difference);

        // DateTimeFormatter
        Scanner console = new Scanner(System.in);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mma");

        System.out.print("Enter a date in month/day/year format: ");
        LocalDate date = LocalDate.parse(console.nextLine(), dateFormat);
        System.out.println(date); // default format

        System.out.print("Enter a time in twelve hour:minute format: ");
        LocalTime time = LocalTime.parse(console.nextLine(), timeFormat);
        System.out.println(time); // default format
    }
}
