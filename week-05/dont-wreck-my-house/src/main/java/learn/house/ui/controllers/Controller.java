package learn.house.ui.controllers;

import learn.house.data.DataException;
import learn.house.ui.View;
import learn.house.ui.menu.MainMenuOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller {

    @Autowired
    private final ControllerGuests controllerGuestsMenu;

    @Autowired
    private final ControllerHosts controllerHostsMenu;

    @Autowired
    private final ControllerReservations controllerReservationsMenu;

    @Autowired
    private final View view;

    @Autowired
    public Controller(ControllerGuests controllerGuestsMenu,
                      ControllerHosts controllerHostsMenu,
                      ControllerReservations controllerReservationsMenu,
                      View view) {
        this.controllerGuestsMenu = controllerGuestsMenu;
        this.controllerHostsMenu = controllerHostsMenu;
        this.controllerReservationsMenu = controllerReservationsMenu;
        this.view = view;
    }

    public void run() {
        view.displayHeader("Welcome to Don't Wreck My House!");
        try {
            runMainMenu();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runMainMenu() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case RESERVATIONS:
                    controllerReservationsMenu.runMenu();
                    break;
                case GUESTS:
                    controllerGuestsMenu.runMenu();
                    break;
                case HOSTS:
                    controllerHostsMenu.runMenu();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }
}
