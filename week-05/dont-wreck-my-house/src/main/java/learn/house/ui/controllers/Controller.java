package learn.house.ui.controllers;

import learn.house.data.DataException;
import learn.house.ui.View;
import learn.house.ui.menu.MainMenuOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller {

    @Autowired
    private final ControllerGuests controllerGuests;

    @Autowired
    private final ControllerHosts controllerHosts;

    @Autowired
    private final ControllerReservations controllerReservations;

    @Autowired
    private final View view;

    @Autowired
    public Controller(ControllerGuests controllerGuests,
                      ControllerHosts controllerHosts,
                      ControllerReservations controllerReservations,
                      View view) {
        this.controllerGuests = controllerGuests;
        this.controllerHosts = controllerHosts;
        this.controllerReservations = controllerReservations;
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
                    controllerReservations.runMenu();
                    break;
                case GUESTS:
                    controllerGuests.runMenu();
                    break;
                case HOSTS:
                    controllerHosts.runMenu();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }
}
