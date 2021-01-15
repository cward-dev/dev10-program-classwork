package learn.house.ui.menu;

public enum ReservationMenuOption {

    EXIT(0, "Return to Main Menu", false),
    MAKE_RESERVATION(1,"Make a Reservation", false),
    EDIT_RESERVATION(2,"Edit a Reservation", false),
    CANCEL_RESERVATION(3,"Cancel a Reservation", false),
    VIEW_RESERVATIONS_FOR_HOST(4, "View Reservations for Host", false),
    VIEW_RESERVATIONS_FOR_INACTIVE_HOST(5, "View Reservations for Inactive Host", false),
    VIEW_RESERVATIONS_FOR_GUEST(6, "View Reservations for Guest", false);

    private final int value;
    private final String message;
    private final boolean hidden;

    ReservationMenuOption(int value, String message, boolean hidden) {
        this.value = value;
        this.message = message;
        this.hidden = hidden;
    }

    public static ReservationMenuOption fromValue(int value) {
        for (ReservationMenuOption option : ReservationMenuOption.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public boolean isHidden() {
        return hidden;
    }
}