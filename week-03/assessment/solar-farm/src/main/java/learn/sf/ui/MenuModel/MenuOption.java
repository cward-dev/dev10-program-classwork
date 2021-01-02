package learn.sf.ui.MenuModel;

public enum MenuOption {

    EXIT("Exit Program"),
    DISPLAY("Display Menu"),
    ADD("Add New Panel"),
    UPDATE("Update Panel"),
    DELETE("Delete Panel");

    private String message;

    MenuOption(String name) { this.message = name; };

    public String getMessage() { return message; }

}
