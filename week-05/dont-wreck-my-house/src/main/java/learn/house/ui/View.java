package learn.house.ui;

import learn.house.models.Host;
import learn.house.models.Reservation;
import learn.house.ui.menu.GuestMenuOption;
import learn.house.ui.menu.HostMenuOption;
import learn.house.ui.menu.MainMenuOption;
import learn.house.ui.menu.ReservationMenuOption;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Component
public class View {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            if (!option.isHidden()) {
                io.printf("%s. %s%n", option.getValue(), option.getMessage());
            }
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public ReservationMenuOption selectReservationMenuOption() {
        displayHeader("Reservation Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (ReservationMenuOption option : ReservationMenuOption.values()) {
            if (!option.isHidden()) {
                io.printf("%s. %s%n", option.getValue(), option.getMessage());
            }
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return ReservationMenuOption.fromValue(io.readInt(message, min, max));
    }

    public HostMenuOption selectHostMenuOption() {
        displayHeader("Host Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (HostMenuOption option : HostMenuOption.values()) {
            if (!option.isHidden()) {
                io.printf("%s. %s%n", option.getValue(), option.getMessage());
            }
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return HostMenuOption.fromValue(io.readInt(message, min, max));
    }

    public GuestMenuOption selectGuestMenuOption() {
        displayHeader("Guest Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (GuestMenuOption option : GuestMenuOption.values()) {
            if (!option.isHidden()) {
                io.printf("%s. %s%n", option.getValue(), option.getMessage());
            }
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return GuestMenuOption.fromValue(io.readInt(message, min, max));
    }

    public String getHostEmail() {
        return io.readRequiredString("Host Email: ");
    }





    // display only
    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }

    public void displayHostInformation(Host host) {
        io.printf("Last Name: %s%n", host.getLastName());
        io.printf("Email: %s%n", host.getEmail());
        io.printf("Phone: %s%n", host.getPhone());
        io.printf("Address: %s, %s, %s %s%n", host.getAddress(), host.getCity(), host.getState().getAbbreviation(), host.getPostalCode());
        io.printf("Standard Rate: $%s per night%n", host.getStandardRate());
        io.printf("Weekend Rate: $%s per night%n", host.getWeekendRate());
    }

    public void displayReservations(List<Reservation> reservations, Host host) { // TODO Delete this tip: fpostlethwaitebh@sakura.ne.jp has reservations for testing
        displayHeader(String.format("Reservations for Host %s (%s)", host.getLastName(), host.getEmail()));
        reservations.stream()
                .sorted(Comparator.comparing(Reservation::getStartDate).thenComparing(Reservation::getEndDate))
                .forEach(r -> io.printf("%10s - %-10s : Guest: %s %s : Total: $%s%n",
                        formatter.format(r.getStartDate()), formatter.format(r.getEndDate()),
                        r.getGuest().getFirstName(), r.getGuest().getLastName(),
                        r.getTotal())
                );
    }

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }
}
