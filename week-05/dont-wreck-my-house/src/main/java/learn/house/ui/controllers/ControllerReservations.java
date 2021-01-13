package learn.house.ui.controllers;

import learn.house.data.DataException;
import learn.house.domain.ReservationService;
import learn.house.ui.View;
import learn.house.ui.menu.HostMenuOption;
import learn.house.ui.menu.ReservationMenuOption;

public class ControllerReservations {

    private final ReservationService service;

    private final View view;

    public ControllerReservations(ReservationService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void runMenu() {
        ReservationMenuOption option;
        do {
            option = view.selectReservationMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS_FOR_HOST:

                    break;
                case MAKE_RESERVATION:

                    break;
                case EDIT_RESERVATION:

                    break;
                case CANCEL_RESERVATION:

                    break;
            }
        } while (option != ReservationMenuOption.EXIT);
    }

}
