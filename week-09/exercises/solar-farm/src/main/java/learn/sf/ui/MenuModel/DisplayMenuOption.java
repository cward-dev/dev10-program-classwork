package learn.sf.ui.MenuModel;

public enum DisplayMenuOption {

    EXIT("Exit Display Menu"),
    DISPLAY_ALL("Display All Panels"),
    DISPLAY_BY_SECTION("Display By Section"),
    DISPLAY_BY_MATERIAL("Display By Material"),
    DISPLAY_BY_ID("Display By Panel Id");

    private String message;

    DisplayMenuOption(String name) { this.message = name; };

    public String getMessage() { return message; }

}
