package learn.house.ui.controllers;

import learn.house.data.DataException;
import learn.house.domain.GuestService;
import learn.house.domain.Result;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.State;
import learn.house.ui.View;
import learn.house.ui.menu.GuestMenuOption;
import learn.house.ui.menu.HostMenuOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ControllerGuests {

    @Autowired
    private final GuestService service;

    @Autowired
    private final View view;

    @Autowired
    private final ControllerHelper helper;

    @Autowired
    public ControllerGuests(GuestService service, View view, ControllerHelper helper) {
        this.service = service;
        this.view = view;
        this.helper = helper;
    }

    public void runMenu() throws DataException {
        GuestMenuOption option;
        do {
            option = view.selectGuestMenuOption();
            switch (option) {
                case ADD_GUEST:
                    addGuest();
                    break;
                case EDIT_GUEST:
                    editGuest();
                    break;
                case VIEW_GUESTS_BY_LAST_NAME:
                    viewGuestsByLastName();
                    break;
                case VIEW_GUESTS_BY_STATE:
                    viewGuestsByState();
                    break;
                case INACTIVATE_GUEST:
                    inactivateGuest();
                    break;
                case REACTIVATE_GUEST:
                    reactivateGuest();
                    break;
            }
        } while (option != GuestMenuOption.EXIT);
    }

    private void addGuest() throws DataException {
        view.displayHeader(GuestMenuOption.ADD_GUEST.getMessage());

        Guest guest = view.makeGuest();
        Result<Guest> result = service.add(guest);

        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = "Guest added successfully.";
            view.displayStatus(true, successMessage);
            view.displayGuestInformation(result.getPayload());
        }

        view.enterToContinue();
    }

    private void editGuest() throws DataException {
        view.displayHeader(GuestMenuOption.EDIT_GUEST.getMessage());

        Guest guest = helper.getGuestByLastName();
        if (guest == null) {
            view.displayStatus(true, "Exiting");
            return;
        }
        Guest updatedGuest = view.updateGuest(guest);
        Result<Guest> result = service.update(updatedGuest);

        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = "Guest updated successfully.";
            view.displayStatus(true, successMessage);
            view.displayGuestInformation(result.getPayload());
        }

        view.enterToContinue();
    }

    private void viewGuestsByLastName() {
        view.displayHeader(GuestMenuOption.VIEW_GUESTS_BY_LAST_NAME.getMessage());

        Guest guest = helper.getGuestByLastName();

        if (guest == null) {
            view.displayStatus(true, "Exiting");
            return;
        }
        view.displayGuestInformation(guest);

        view.enterToContinue();
    }

    private void viewGuestsByState() {
        view.displayHeader(GuestMenuOption.VIEW_GUESTS_BY_STATE.getMessage());

        State state = view.getState();
        List<Guest> guests = service.findByState(state);

        if (guests.size() == 0) {
            view.displayStatus(false, String.format("No guests found in %s.", state.getAbbreviation()));
            view.enterToContinue();
            return;
        }
        view.displayGuests(guests);

        view.enterToContinue();
    }

    private void inactivateGuest() throws DataException {
        view.displayHeader(GuestMenuOption.INACTIVATE_GUEST.getMessage());

        Result<Guest> result = new Result<>();
        Guest guest = helper.getGuestByLastName();
        if (guest == null) {
            result.addErrorMessage("Exiting");
            view.displayStatus(false, result.getErrorMessages());
            view.enterToContinue();
            return;
        }

        view.displayGuestInformation(guest);

        boolean inactivateGuest = view.confirmGuestInactivation();
        if (inactivateGuest) {
            result = service.delete(guest);
            if (result.isSuccess()) {
                String successMessage = "Guest inactivated successfully.";
                view.displayStatus(true, successMessage);
            } else {
                view.displayStatus(false, result.getErrorMessages());
            }
        } else {
            result.addErrorMessage("Guest not inactivated.");
            view.displayStatus(false, result.getErrorMessages());
        }

        view.enterToContinue();
    }

    private void reactivateGuest() throws DataException {
        view.displayHeader(GuestMenuOption.REACTIVATE_GUEST.getMessage());

        Result<Guest> result = new Result<>();
        Guest guest = helper.getInactiveGuestByLastName();

        if (guest == null) {
            view.displayStatus(true, "Exiting.");
            view.enterToContinue();
            return;
        }

        view.displayGuestInformation(guest);

        boolean reactivateGuest = view.confirmGuestReactivation();
        if (reactivateGuest) {
            result = service.add(guest);
            if (result.isSuccess()) {
                String successMessage = "Guest reactivated successfully.";
                view.displayStatus(true, successMessage);
            } else {
                view.displayStatus(false, result.getErrorMessages());
            }
        } else {
            result.addErrorMessage("Guest not reactivated.");
            view.displayStatus(false, result.getErrorMessages());
        }

        view.enterToContinue();
    }
}