package learn.house.ui;

import learn.house.models.Host;
import learn.house.models.Reservation;
import learn.house.models.State;
import learn.house.ui.menu.GuestMenuOption;
import learn.house.ui.menu.HostMenuOption;
import learn.house.ui.menu.MainMenuOption;
import learn.house.ui.menu.ReservationMenuOption;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

        String message = String.format("Select [%s-%s]: ", min, max);
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

        String message = String.format("Select [%s-%s]: ", min, max);
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

        String message = String.format("Select [%s-%s]: ", min, max);
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

        String message = String.format("Select [%s-%s]: ", min, max);
        return GuestMenuOption.fromValue(io.readInt(message, min, max));
    }

    public String getFirstName(String label) {
        return io.readRequiredString(String.format("%s Last Name: ", label));
    }

    public String getLastName(String label) {
        return io.readRequiredString(String.format("%s Last Name: ", label));
    }

    public State getState() {
        return io.readState("State name or abbreviation: ");
    }

    public Host chooseHost(List<Host> hosts) {
        displayHosts(hosts);

        String message = String.format("Select Host [%s-%s]: ", 1, hosts.size());

        int selection = io.readInt(message, 1, hosts.size());

        return hosts.stream()
                .sorted(Comparator.comparing(Host::getLastName).thenComparing(Host::getEmail))
                .skip(selection - 1)
                .findFirst()
                .orElse(null);
    }

    public Reservation chooseReservation(List<Reservation> reservations, Host host) {
        displayReservationsByStartDate(reservations, host);

        String message = String.format("Select Reservation [%s-%s]: ", 1, reservations.size());

        int selection = io.readInt(message, 1, reservations.size());

        return reservations.stream()
                .sorted(Comparator.comparing(Reservation::getStartDate))
                .skip(selection - 1)
                .findFirst()
                .orElse(null);
    }

    public String getHostEmail() {
        return io.readRequiredString("Host Email: ");
    }

    // Overloaded for editing existing
    public String getHostEmail(String existingEmail) {
        String email = io.readString(String.format("Host Email [%s]: ", existingEmail));
        if (email == null || email.trim().length() == 0) {
            return existingEmail;
        }
        return email;
    }

    public String getGuestEmail() {
        return io.readRequiredString("Guest Email: ");
    }

    // Overloaded for editing existing
    public String getGuestEmail(String existingEmail) {
        String email = io.readString(String.format("Guest Email [%s]: ", existingEmail));
        if (email == null || email.trim().length() == 0) {
            return existingEmail;
        }
        return email;
    }

    public LocalDate getStartDate() {
        return io.readLocalDateStart("Start Date [mm/dd/yyyy]: ");
    }

    // Overloaded for editing existing
    public LocalDate getStartDate(LocalDate existingDate) {
        return io.readLocalDateStart(String.format("Start Date [%s]: ", formatter.format(existingDate)), existingDate);
    }

    public LocalDate getEndDate(LocalDate startDate) {
        return io.readLocalDateEnd("End Date [mm/dd/yyyy]: ", startDate);
    }

    // Overloaded for editing existing
    public LocalDate getEndDate(LocalDate startDate, LocalDate existingDate) {
        return io.readLocalDateEnd(String.format("End Date [%s]: ", formatter.format(existingDate)), startDate, existingDate);
    }

    public boolean confirmDeletion(Reservation reservation) {
        return io.readBoolean("Are you sure you wish to delete this reservation? [y/n]: ");
    }

    // display only
    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displaySubHeader(String message) {
        io.println("");
        io.println(message);
        io.println("-".repeat(message.length()));
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
        displaySubHeader("HOST INFORMATION");
        io.printf("Last Name: %s%n", host.getLastName());
        io.printf("Email: %s%n", host.getEmail());
        io.printf("Phone: %s%n", host.getPhone());
        io.printf("Address: %s, %s, %s %s%n", host.getAddress(), host.getCity(), host.getState().getAbbreviation(), host.getPostalCode());
        io.printf("Standard Rate: $%s per night%n", host.getStandardRate());
        io.printf("Weekend Rate: $%s per night%n", host.getWeekendRate());
    }

    public void displayHosts(List<Host> hosts) {
        displayHeader("HOSTS");
        AtomicInteger count = new AtomicInteger();
        hosts.stream()
                .sorted(Comparator.comparing(Host::getLastName).thenComparing(Host::getEmail))
                .limit(25)
                .forEach(h -> {
                    count.getAndIncrement();
                    io.printf("%3s. %s (%s)%n", count.get(), h.getLastName(), h.getEmail());
                });

        if (hosts.size() > 25) {
            io.println("More than 25 hosts found. Showing first 25. Please refine your search.");
        }
    }

    public void displayReservationsByStartDate(List<Reservation> reservations, Host host) { // TODO Delete this tip: fpostlethwaitebh@sakura.ne.jp has reservations for testing
        displayHostInformation(host);

        displaySubHeader("RESERVATIONS");

        displaySubHeader(String.format("%3s | %-10s | %-10s | %-12s | %9s | %-25s",
                "#", "Start", "End", "Guest Name", "Total", "Guest Email"));

        AtomicInteger counter = new AtomicInteger();
        reservations.stream()
                .sorted(Comparator.comparing(Reservation::getStartDate))
                .forEach(r -> {
                    counter.getAndIncrement();
                    io.printf("%3s | %10s | %-10s | %1s %-10s | %9s | %s%n",
                        counter,
                        formatter.format(r.getStartDate()),
                        formatter.format(r.getEndDate()),
                        r.getGuest().getFirstName().charAt(0),
                        r.getGuest().getLastName().substring(0,Math.min(r.getGuest().getLastName().length(), 10)),
                        "$" + r.getTotal(),
                        r.getGuest().getEmail());});
    }

    public void displayReservation(Reservation reservation) {
        displaySubHeader("         RESERVATION         ");
        io.printf("   Host: %s%n", reservation.getHost().getLastName());
        io.printf("         %s%n", reservation.getHost().getEmail());
        io.printf("Address: %s%n",
                reservation.getHost().getAddress());
        io.printf("         %s, %s %s%n",
                reservation.getHost().getCity(),
                reservation.getHost().getState().getAbbreviation(),
                reservation.getHost().getPostalCode());
        io.println("");
        io.printf("  Guest: %s %s%n", reservation.getGuest().getFirstName(), reservation.getGuest().getLastName());
        io.printf("         %s%n", reservation.getGuest().getEmail());
        io.println("");
        io.printf("  Start: %s%n", formatter.format(reservation.getStartDate()));
        io.printf("    End: %s%n", formatter.format(reservation.getEndDate()));
        io.printf("  Total: $%s%n", reservation.getTotal());
    }

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }
}
