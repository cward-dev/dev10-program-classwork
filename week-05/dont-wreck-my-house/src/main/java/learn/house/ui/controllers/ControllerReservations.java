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
    private final ControllerHelper helper;

    @Autowired
    private final View view;

    @Autowired
    public ControllerReservations(ReservationService service, View view, ControllerHelper helper) {
        this.service = service;
        this.view = view;
        this.helper = helper;
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

    private void viewReservationsForHost() {
        view.displayHeader(ReservationMenuOption.VIEW_RESERVATIONS_FOR_HOST.getMessage());
        Host host = helper.getHostByLastName();

        List<Reservation> reservations = service.findByHost(host);

        if (reservations.size() == 0) {
            view.displayStatus(false, String.format("No reservations found for Host %s (%s)",
                    host.getLastName(),
                    host.getEmail()));
        } else {
            view.displayReservationsByStartDate(reservations, host);
        }

        view.enterToContinue();
    }

    private void makeReservation() throws DataException {
        view.displayHeader(ReservationMenuOption.MAKE_RESERVATION.getMessage());

        Host host = helper.getHostByLastName();
        view.displayHostInformation(host);

        Guest guest = helper.getGuestByLastName();
        view.displayGuestInformation(guest);

        LocalDate startDate = view.getStartDate();
        LocalDate endDate = view.getEndDate(startDate);

        Reservation reservation = new Reservation(startDate, endDate, host, guest);

        Result<Reservation> result = service.add(reservation);
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

        Result<Reservation> reservation = helper.getReservationByHostLastName();
        if (!reservation.isSuccess()) {
            view.displayStatus(false, reservation.getErrorMessages());
            view.enterToContinue();
            return;
        }

        reservation.getPayload().setGuest(
                helper.getGuestByLastName(reservation.getPayload().getGuest()));
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

        Result<Reservation> reservation = helper.getReservationByHostLastName();
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
}
