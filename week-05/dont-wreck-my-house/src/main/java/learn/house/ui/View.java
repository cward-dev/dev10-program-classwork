package learn.house.ui;

import learn.house.ui.menu.GuestMenuOption;
import learn.house.ui.menu.HostMenuOption;
import learn.house.ui.menu.MainMenuOption;
import learn.house.ui.menu.ReservationMenuOption;

import java.util.List;

public class View {

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
}
