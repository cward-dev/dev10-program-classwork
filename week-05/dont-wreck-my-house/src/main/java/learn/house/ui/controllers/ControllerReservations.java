package learn.house.ui.controllers;

import learn.house.data.DataException;
import learn.house.domain.GuestService;
import learn.house.domain.HostService;
import learn.house.domain.ReservationService;
import learn.house.domain.Result;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import learn.house.ui.View;
import learn.house.ui.menu.ReservationMenuOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ControllerReservations {

    @Autowired
    private final ReservationService service;

    @Autowired
    private final HostService hostService;

    @Autowired
    private final GuestService guestService;

    @Autowired
    private final View view;

    @Autowired
    public ControllerReservations(ReservationService service, HostService hostService, GuestService guestService, View view) {
        this.service = service;
        this.hostService = hostService;
        this.guestService = guestService;
        this.view = view;
    }

    public void runMenu() throws DataException {
        ReservationMenuOption option;
        do {
            option = view.selectReservationMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS_FOR_HOST:
                    viewReservationsForHost();
                    break;
                case MAKE_RESERVATION:
                    makeReservation();
                    break;
                case EDIT_RESERVATION:
                    editReservation();
                    break;
                case CANCEL_RESERVATION:
                    cancelReservation();
                    break;
            }
        } while (option != ReservationMenuOption.EXIT);
    }

    private void viewReservationsForHost() { // TODO: just displays Host Information as a test right now, need to fix
        view.displayHeader(ReservationMenuOption.VIEW_RESERVATIONS_FOR_HOST.getMessage());
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        if (host == null) {
            view.displayStatus(false, String.format("No host found with email address '%s'.",
                    hostEmail));
        } else {
            List<Reservation> reservations = service.findByHost(host);
            view.displayReservationsByStartDate(reservations, host);
        }
        view.enterToContinue();
    }

    private void makeReservation() throws DataException {
        view.displayHeader(ReservationMenuOption.MAKE_RESERVATION.getMessage());

        Host host = getHostByEmail();
        Guest guest = getGuestByEmail();
        LocalDate startDate = view.getStartDate();
        LocalDate endDate = view.getEndDate(startDate);

        Reservation reservation = new Reservation(startDate, endDate, host, guest);

        Result<Reservation> result = getReservationByHostEmail();
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = "Reservation created successfully.";
            view.displayStatus(true, successMessage);
            view.displayReservation(reservation);
        }

        view.enterToContinue();
    }

    private void editReservation() throws DataException {
        view.displayHeader(ReservationMenuOption.EDIT_RESERVATION.getMessage());

        Result<Reservation> reservation = getReservationByHostEmail();
        if (!reservation.isSuccess()) {
            view.displayStatus(false, reservation.getErrorMessages());
            view.enterToContinue();
            return;
        }

        reservation.getPayload().setGuest(
                getGuestByEmail(reservation.getPayload().getGuest()));
        reservation.getPayload().setStartDate(
                view.getStartDate(reservation.getPayload().getStartDate()));
        reservation.getPayload().setEndDate(
                view.getEndDate(reservation.getPayload().getStartDate(), reservation.getPayload().getEndDate()));

        Result<Reservation> result = service.update(reservation.getPayload());

        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = "Reservation updated successfully.";
            view.displayStatus(true, successMessage);
            view.displayReservation(result.getPayload());
        }

        view.enterToContinue();
    }

    private void cancelReservation() throws DataException {
        view.displayHeader(ReservationMenuOption.CANCEL_RESERVATION.getMessage());

        Result<Reservation> reservation = getReservationByHostLastName();

        if (!reservation.isSuccess()) {
            view.displayStatus(false, reservation.getErrorMessages());
            view.enterToContinue();
            return;
        }

        boolean cancelReservation = view.confirmDeletion(reservation.getPayload());
        Result<Reservation> result = new Result<>();
        if (cancelReservation) {
            result = service.delete(reservation.getPayload());
            if (result.isSuccess()) {
                String successMessage = "Reservation deleted successfully.";
                view.displayStatus(true, successMessage);
            } else {
                view.displayStatus(false, result.getErrorMessages());
            }
        } else {
            result.addErrorMessage("Reservation not deleted.");
            view.displayStatus(false, result.getErrorMessages());
        }

        view.enterToContinue();
    }

    private Host getHostByEmail() {
        String hostEmail;
        Host host;

        do {
            hostEmail = view.getHostEmail();
            host = hostService.findByEmail(hostEmail);
            if (host == null) {
                String errorMessage = String.format("No host exists with email '%s'", hostEmail);
                view.displayStatus(false, errorMessage);
            }
        } while (host == null);

        return host;
    }

    // Overloaded for editing existing
    private Host getHostByEmail(Host existingHost) {
        String hostEmail;
        Host host;

        do {
            hostEmail = view.getHostEmail(existingHost.getEmail());
            host = hostService.findByEmail(hostEmail);
            if (host == null) {
                String errorMessage = String.format("No host exists with email '%s'", hostEmail);
                view.displayStatus(false, errorMessage);
            }
        } while (host == null);

        return host;
    }

    private Guest getGuestByEmail() {
        String guestEmail;
        Guest guest;

        do {
            guestEmail = view.getGuestEmail();
            guest = guestService.findByEmail(guestEmail);
            if (guest == null) {
                String errorMessage = String.format("No guest exists with email '%s'.", guestEmail);
                view.displayStatus(false, errorMessage);
            }
        } while (guest == null);

        return guest;
    }

    // Overloaded for editing existing
    private Guest getGuestByEmail(Guest existingGuest) {
        String guestEmail;
        Guest guest;

        do {
            guestEmail = view.getGuestEmail(existingGuest.getEmail());
            guest = guestService.findByEmail(guestEmail);
            if (guest == null) {
                String errorMessage = String.format("No guest exists with email '%s'.", guestEmail);
                view.displayStatus(false, errorMessage);
            }
        } while (guest == null);

        return guest;
    }

    private Result<Reservation> getReservationByHostEmail() throws DataException {
        Result<Reservation> result = new Result<>();
        Host host = getHostByEmail();
        List<Reservation> reservations = service.findByHost(host);

        if (reservations.size() == 0) {
            result.addErrorMessage(String.format("No reservations under Host %s (%s).", host.getLastName(), host.getEmail()));
            return result;
        }
        result.setPayload(view.chooseReservation(reservations, host));

        return result;
    }

    private Result<Reservation> getReservationByHostLastName() {
        Result<Reservation> result = new Result<>();
        String hostLastName = view.getLastName("Host");
        List<Host> hosts = hostService.findByLastName(hostLastName);

        if (hosts.size() == 0) {
            result.addErrorMessage(String.format("No hosts matching last name '%s'.", hostLastName));
            return result;
        }

        Host host;
        if (hosts.size() == 1) {
            host = hosts.get(0);
        } else {
            host = view.chooseHost(hosts);
        }

        List<Reservation> reservations = service.findByHost(host);

        if (reservations.size() == 0) {
            result.addErrorMessage(String.format("No reservations under Host %s (%s).", host.getLastName(), host.getEmail()));
            return result;
        }
        result.setPayload(view.chooseReservation(reservations, host));

        return result;
    }

}
