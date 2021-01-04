import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Exercise01 {

    // LocalDate
    // ========================
    // Complete the numbered tasks below.
    // Open and execute the accompanying tests to confirm your solution is correct.

    // 1. return today's date
    LocalDate getToday() {
        return LocalDate.now();
    }

    // 2. return December 17, 1903 as a LocalDate
    LocalDate getFirstFlightDate() {
        return LocalDate.of(1903, 12, 17);
    }

    // 3. if parameter is in the future, return null.
    // Otherwise, add 5 days to the parameter and return the result.
    LocalDate makeFutureNullShiftThePast(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            return null;
        }

        return date.plusDays(5);
    }

    // 4. return the fifth Friday from the parameter date.
    // if the date is Friday, don't count it.
    LocalDate fiveFridaysFromDate(LocalDate date) {
        DayOfWeek day;

        do {
            date = date.plusDays(1);
            day = date.getDayOfWeek();
        } while (day != DayOfWeek.FRIDAY);

        return date.plusWeeks(5);
    }

    // 5. given a date and a count,
    // return a list of the next `fridayCount` Fridays after the date.
    // if the date is Friday, don't include it.
    List<LocalDate> getNextFridays(LocalDate date, int fridayCount) {
        List<LocalDate> result = new ArrayList<>();
        DayOfWeek day;

        do {
            date = date.plusDays(1);
            day = date.getDayOfWeek();
        } while (day != DayOfWeek.FRIDAY);

        for (int i = 0; i < fridayCount; i++) {
            result.add(date.plusWeeks(i));
        }

        return result;
    }

    // 6. return the absolute value of the days between two dates.
    // one may be before two, two may be before one, but neither will be null
    int getDaysBetween(LocalDate one, LocalDate two) {
        if (one.isBefore(two)) {
            return (int) one.datesUntil(two).count();
        }

        return (int) two.datesUntil(one).count();
    }
}
