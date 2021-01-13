package learn.house.ui.controllers;

import learn.house.data.DataException;
import learn.house.domain.GuestService;
import learn.house.domain.ReservationService;
import learn.house.ui.View;
import learn.house.ui.menu.GuestMenuOption;
import learn.house.ui.menu.HostMenuOption;

public class ControllerGuests {

    private final GuestService service;

    private final View view;

    public ControllerGuests(GuestService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void runMenu() {
        GuestMenuOption option;
        do {
            option = view.selectGuestMenuOption();
            switch (option) {
                case VIEW_GUESTS:

                    break;
                case ADD_GUEST:

                    break;
                case EDIT_GUEST:

                    break;
                case INACTIVATE_GUEST:

                    break;
            }
        } while (option != GuestMenuOption.EXIT);
    }

}
