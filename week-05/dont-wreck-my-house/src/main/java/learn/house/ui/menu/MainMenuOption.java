package learn.house.ui.menu;

public enum MainMenuOption {

    EXIT(0, "Exit", false),
    RESERVATIONS(1, "Reservations Menu", false),
    GUESTS(2,"Guests Menu", false),
    HOSTS(3,"Hosts Menu", false);

    private final int value;
    private final String message;
    private final boolean hidden;

    MainMenuOption(int value, String message, boolean hidden) {
        this.value = value;
        this.message = message;
        this.hidden = hidden;
    }

    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption option : MainMenuOption.values()) {
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
