package learn.house.ui.menu;

public enum GuestMenuOption {

    EXIT(0, "Return to Main Menu", false),
    ADD_GUEST(1,"Add a Guest", false),
    EDIT_GUEST(2,"Edit a Guest", false),
    VIEW_GUESTS(3, "View Guests", false),
    INACTIVATE_GUEST(4,"Inactivate a Guest", false),
    REACTIVATE_GUEST(5,"Reactivate a Guest", false);

    private final int value;
    private final String message;
    private final boolean hidden;

    GuestMenuOption(int value, String message, boolean hidden) {
        this.value = value;
        this.message = message;
        this.hidden = hidden;
    }

    public static GuestMenuOption fromValue(int value) {
        for (GuestMenuOption option : GuestMenuOption.values()) {
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
