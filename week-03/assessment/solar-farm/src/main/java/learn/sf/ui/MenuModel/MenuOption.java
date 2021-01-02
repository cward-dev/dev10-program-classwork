package learn.sf.ui.MenuModel;

public enum MenuOption {

    EXIT("Exit Program"),
    DISPLAY("Display Menu"),
    ADD("Add a Panel"),
    UPDATE("Update a Panel"),
    DELETE("Delete a Panel");

    private String message;

    MenuOption(String name) { this.message = name; };

    public String getMessage() { return message; }

}
