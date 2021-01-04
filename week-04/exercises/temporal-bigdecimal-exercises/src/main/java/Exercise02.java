import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Exercise02 {

    // LocalTime
    // ========================
    // Complete the numbered tasks below.
    // Open and execute the accompanying tests to confirm your solution is correct.

    // 1. return the current time as a LocalTime
    LocalTime getNow() {
        return LocalTime.now();
    }

    // 2. return 4PM (tea time!) as a LocalTime.
    LocalTime getTeaTime() {
        return LocalTime.of(16, 0);
    }

    // 3. add 12 hours to the time parameter and return the value
    LocalTime add12Hours(LocalTime time) {
        return time.plusHours(12);
    }

    // 4. given a time parameter, return a list of the next 4
    // quarter-hour appointments available after the time.
    // appointment times should not include seconds even if the time parameter does.
    // ignore seconds.
    // Examples:
    // time == 16:07:32
    // appointments == 16:15, 16:30, 16:45, 17:00
    //
    // time == 03:00:01
    // appointments == 03:00, 03:15, 03:30, 03:45
    //
    // time == 04:30:00
    // appointments == 04:30, 04:45, 05:00, 05:15
    List<LocalTime> getQuarterHourAppointments(LocalTime time) {
        List<LocalTime> appointmentTimes = new ArrayList<>();

        LocalTime appointment;
        if (time.getMinute() == 0) appointment = LocalTime.of(time.getHour(), 0);
        else if (time.getMinute() <= 15) appointment = LocalTime.of(time.getHour(), 15);
        else if (time.getMinute() <= 30) appointment = LocalTime.of(time.getHour(), 30);
        else if (time.getMinute() <= 45) appointment = LocalTime.of(time.getHour(), 45);
        else appointment = LocalTime.of(time.plusHours(1).getHour(), 0);

        LocalTime end = appointment.plusHours(1);

        for (; appointment.compareTo(end) < 0; appointment = appointment.plusMinutes(15) ) {
            appointmentTimes.add(appointment);
        }

        return appointmentTimes;
    }
}
