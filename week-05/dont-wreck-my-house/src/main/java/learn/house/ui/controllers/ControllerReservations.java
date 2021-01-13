package learn.house.ui.controllers;

import learn.house.domain.HostService;
import learn.house.domain.ReservationService;
import learn.house.models.Host;
import learn.house.models.Reservation;
import learn.house.ui.View;
import learn.house.ui.menu.ReservationMenuOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ControllerReservations {

    @Autowired
    private final ReservationService service;

    @Autowired
    private final HostService hostService;

    @Autowired
    private final View view;

    @Autowired
    public ControllerReservations(ReservationService service, HostService hostService, View view) {
        this.service = service;
        this.hostService = hostService;
        this.view = view;
    }

    public void runMenu() {
        ReservationMenuOption option;
        do {
            option = view.selectReservationMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS_FOR_HOST:
                    viewReservationsForHost();
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

    private void viewReservationsForHost() {
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        if (host != null) {
            List<Reservation> all = service.findByHost(host);
            view.displayHostInformation(host);
        } else {
            view.displayStatus(false, String.format("No host found with email address '%s'.",
                    hostEmail));
        }
        view.enterToContinue();
    }
}
