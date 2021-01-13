package learn.house.ui.controllers;

import learn.house.domain.GuestService;
import learn.house.ui.View;
import learn.house.ui.menu.GuestMenuOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControllerGuests {

    @Autowired
    private final GuestService service;

    @Autowired
    private final View view;

    @Autowired
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
