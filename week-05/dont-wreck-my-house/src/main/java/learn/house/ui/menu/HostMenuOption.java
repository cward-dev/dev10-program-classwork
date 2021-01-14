package learn.house.ui.menu;

public enum HostMenuOption {

    EXIT(0, "Return to Main Menu", false),
    ADD_HOST(1,"Add a Host", false),
    EDIT_HOST(2,"Edit a Host", false),
    VIEW_HOSTS_BY_LAST_NAME(3, "View Hosts By Last Name", false),
    VIEW_HOSTS_BY_STATE(4, "View Hosts By State", false),
    INACTIVATE_HOST(5,"Inactivate a Host", false),
    REACTIVATE_HOST(6, "Reactivate a Host", false);

    private final int value;
    private final String message;
    private final boolean hidden;

    HostMenuOption(int value, String message, boolean hidden) {
        this.value = value;
        this.message = message;
        this.hidden = hidden;
    }

    public static HostMenuOption fromValue(int value) {
        for (HostMenuOption option : HostMenuOption.values()) {
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