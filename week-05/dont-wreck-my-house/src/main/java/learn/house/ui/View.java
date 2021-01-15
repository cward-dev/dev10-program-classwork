package learn.house.ui;

import learn.house.models.Guest;
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
import java.util.stream.Collectors;

@Component
public class View {

    private final LocalDate today = LocalDate.now();
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

    public State getState() {
        return io.readState("State name or abbreviation: ");
    }

    public State getState(State existingState) {
        return io.readState(String.format("State name or abbreviation [%s]: ", existingState), existingState);
    }

    public Host chooseHost(List<Host> hosts) {
        displayHosts(hosts);

        int min = 0;
        int max = Math.min(hosts.size(), 15);

        String message = String.format("Select Host [%s-%s]: ", min, max);

        int selection = io.readInt(message, min, max);

        if (selection == 0) {
            return null;
        }

        return hosts.stream()
                .sorted(Comparator.comparing(Host::getLastName).thenComparing(Host::getEmail))
                .skip(selection - 1)
                .findFirst()
                .orElse(null);
    }

    public Guest chooseGuest(List<Guest> guests) {
        displayGuests(guests);

        int min = 0;
        int max = Math.min(guests.size(), 15);

        String message = String.format("Select Guest [%s-%s]: ", min, max);

        int selection = io.readInt(message, min, max);

        if (selection == 0) {
            return null;
        }

        return guests.stream()
                .sorted(Comparator.comparing(Guest::getLastName).thenComparing(Guest::getFirstName).thenComparing(Guest::getEmail))
                .skip(selection - 1)
                .findFirst()
                .orElse(null);
    }

    public Reservation chooseReservation(List<Reservation> reservations, Host host) {
        List<Reservation> eligibleReservations = reservations.stream()
                .filter(r -> !r.getStartDate().isBefore(today))
                .collect(Collectors.toList());

        displayReservationsByStartDate(eligibleReservations, host);

        String message = String.format("Select Reservation [%s-%s]: ", 1, eligibleReservations.size());

        int selection = io.readInt(message, 1, eligibleReservations.size());

        return eligibleReservations.stream()
                .filter(r -> !r.getStartDate().isBefore(today))
                .sorted(Comparator.comparing(Reservation::getStartDate))
                .skip(selection - 1)
                .findFirst()
                .orElse(null);
    }

    public String getStringValue(String label) {
        return io.readRequiredString(String.format("%s: ", label));
    }

    // Overloaded for editing existing
    public String getStringValue(String label, String existingValue) {
        String value = io.readString(String.format("%s [%s]: ", label, existingValue));
        if (value == null || value.trim().length() == 0) {
            return existingValue;
        }
        return value;
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

    public boolean confirmHostInactivation() {
        return io.readBoolean("Are you sure you wish to inactivate this host? [y/n]: ");
    }

    public boolean confirmHostReactivation() {
        return io.readBoolean("Are you sure you wish to reactivate this host? [y/n]: ");
    }

    public boolean confirmGuestInactivation() {
        return io.readBoolean("Are you sure you wish to inactivate this guest? [y/n]: ");
    }

    public boolean confirmGuestReactivation() {
        return io.readBoolean("Are you sure you wish to reactivate this guest? [y/n]: ");
    }

    public Host makeHost() {
        Host host = new Host();
        host.setLastName(getStringValue("Host Last Name"));
        host.setEmail(getStringValue("Host Email"));
        host.setPhone(getStringValue("Host Phone Number"));
        host.setAddress(getStringValue("Host Street Address"));
        host.setCity(getStringValue("Host City"));
        host.setState(io.readState("Host State Name/Abbreviation: "));
        host.setPostalCode(getStringValue("Host Postal Code [12345]"));
        host.setStandardRate(io.readBigDecimal("Standard Rate: $"));
        host.setWeekendRate(io.readBigDecimal("Weekend Rate: $"));
        return host;
    }

    public Host updateHost(Host host) {
        Host updatedHost = new Host();

        updatedHost.setId(host.getId());
        updatedHost.setLastName(getStringValue("Host Last Name", host.getLastName()));
        updatedHost.setEmail(getStringValue("Host Email", host.getEmail()));
        updatedHost.setPhone(getStringValue("Host Phone Number", host.getPhone()));
        updatedHost.setAddress(getStringValue("Host Street Address", host.getAddress()));
        updatedHost.setCity(getStringValue("Host City", host.getCity()));
        updatedHost.setState(io.readState(String.format("Host State [%s]: ", host.getState().getAbbreviation()), host.getState()));
        updatedHost.setPostalCode(getStringValue("Host Postal Code", host.getPostalCode()));
        updatedHost.setStandardRate(io.readBigDecimal(String.format("Standard Rate [$%s]: $", host.getStandardRate()), host.getStandardRate()));
        updatedHost.setWeekendRate(io.readBigDecimal(String.format("Weekend Rate [$%s]: $", host.getWeekendRate()), host.getWeekendRate()));

        return updatedHost;
    }

    public Guest makeGuest() {
        Guest guest = new Guest();
        guest.setFirstName(getStringValue("First Name"));
        guest.setLastName(getStringValue("Last Name"));
        guest.setEmail(getStringValue("Email"));
        guest.setPhone(getStringValue("Phone Number"));
        guest.setState(io.readState("State Name/Abbreviation: "));
        return guest;
    }

    public Guest updateGuest(Guest guest) {
        Guest updatedGuest = new Guest();

        updatedGuest.setId(guest.getId());
        updatedGuest.setFirstName(getStringValue("First Name", guest.getFirstName()));
        updatedGuest.setLastName(getStringValue("Last Name", guest.getLastName()));
        updatedGuest.setEmail(getStringValue("Email", guest.getEmail()));
        updatedGuest.setPhone(getStringValue("Phone Number", guest.getPhone()));
        updatedGuest.setState(io.readState(String.format("State [%s]: ", guest.getState()), guest.getState()));

        return updatedGuest;
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
        io.printf("    Name: %s%n", host.getLastName());
        io.printf("   Email: %s%n", host.getEmail());
        io.printf("   Phone: %s%n", host.getPhone());
        io.printf(" Address: %s, %s, %s %s%n", host.getAddress(), host.getCity(), host.getState().getAbbreviation(), host.getPostalCode());
        io.printf("Standard: $%s per night%n", host.getStandardRate());
        io.printf(" Weekend: $%s per night%n", host.getWeekendRate());
    }

    public void displayHosts(List<Host> hosts) {
        int maxDisplayed = 15;

        displayHeader("HOSTS");
        AtomicInteger count = new AtomicInteger();
        hosts.stream()
                .sorted(Comparator.comparing(Host::getLastName).thenComparing(Host::getEmail))
                .limit(maxDisplayed)
                .forEach(h -> {
                    count.getAndIncrement();
                    io.printf("%2s. %s (%s)%n", count.get(), h.getLastName(), h.getEmail());
                });

        io.println("  * Press [0] to search again");
        if (hosts.size() > maxDisplayed) {
            io.println("");
            io.printf("More than %s hosts found. Showing first %s. Enter [0] to refine your search.%n", maxDisplayed, maxDisplayed);
        }
    }

    public void displayGuestInformation(Guest guest) {
        displaySubHeader("GUEST INFORMATION");
        io.printf(" Name: %s, %s%n", guest.getLastName(), guest.getFirstName());
        io.printf("Email: %s%n", guest.getEmail());
        io.printf("Phone: %s%n", guest.getPhone());
        io.printf("State: %s%n", guest.getState().getAbbreviation());
    }

    public void displayGuests(List<Guest> guests) {
        int maxDisplayed = 15;

        displayHeader("GUESTS");
        AtomicInteger count = new AtomicInteger();
        guests.stream()
                .sorted(Comparator.comparing(Guest::getLastName).thenComparing(Guest::getFirstName).thenComparing(Guest::getEmail))
                .limit(maxDisplayed)
                .forEach(g -> {
                    count.getAndIncrement();
                    io.printf("%2s. %s, %s (%s)%n", count.get(), g.getLastName(), g.getFirstName(), g.getEmail());
                });

        io.println("  * Press [0] to search again");
        if (guests.size() > maxDisplayed) {
            io.println("");
            io.printf("More than %s hosts found. Showing first %s. Enter [0] to refine your search.%n", maxDisplayed, maxDisplayed);
        }
    }

    public void displayReservationsByStartDate(List<Reservation> reservations, Host host) {
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
        displaySubHeader(String.format("RESERVATION (%s-%s)",
                reservation.getHost().getLastName().toUpperCase(),
                reservation.getId()));

        io.printf("Address: %s%n",
                reservation.getHost().getAddress());
        io.printf("         %s, %s %s%n",
                reservation.getHost().getCity(),
                reservation.getHost().getState().getAbbreviation(),
                reservation.getHost().getPostalCode());
        io.println("");

        io.printf("   Host: %s%n", reservation.getHost().getLastName());
        io.printf("         %s%n", reservation.getHost().getEmail());
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
