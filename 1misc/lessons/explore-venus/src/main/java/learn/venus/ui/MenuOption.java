package learn.venus.ui;

public enum MenuOption {

    EXIT("Exit"),
    DISPLAY_ORBITERS("Display Orbiters"),
    CREATE_ORBITER("Create Orbiter"),
    UPDATE_ORBITER("Update Orbiter"),
    DELETE_ORBITER("Delete Orbiter");

    private final String title;

    MenuOption(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
