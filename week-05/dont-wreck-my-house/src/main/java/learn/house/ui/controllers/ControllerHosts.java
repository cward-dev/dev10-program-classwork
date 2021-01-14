package learn.house.ui.controllers;

import learn.house.data.DataException;
import learn.house.domain.HostService;
import learn.house.domain.Result;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import learn.house.models.State;
import learn.house.ui.View;
import learn.house.ui.menu.HostMenuOption;
import learn.house.ui.menu.ReservationMenuOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
public class ControllerHosts {

    @Autowired
    private final HostService service;

    @Autowired
    private final View view;

    @Autowired
    private final ControllerHelper helper;

    @Autowired
    public ControllerHosts(HostService service, View view, ControllerHelper helper) {
        this.service = service;
        this.view = view;
        this.helper = helper;
    }

    public void runMenu() throws DataException {
        HostMenuOption option;
        do {
            option = view.selectHostMenuOption();
            switch (option) {
                case VIEW_HOSTS_BY_STATE:
                    viewHostsByState();
                    break;
                case ADD_HOST:
                    addHost();
                    break;
                case EDIT_HOST:
                    editHost();
                    break;
                case INACTIVATE_HOST:
                    inactivateHost();
                    break;
                case REACTIVATE_HOST:
                    reactivateHost();
                    break;
            }
        } while (option != HostMenuOption.EXIT);
    }

    private void viewHostsByState() {
        view.displayHeader(HostMenuOption.VIEW_HOSTS_BY_STATE.getMessage());

        State state = view.getState();
        List<Host> hosts = service.findByState(state);

        if (hosts.size() == 0) {
            view.displayStatus(false, String.format("No hosts found in %s.", state.getAbbreviation()));
            view.enterToContinue();
            return;
        }
        view.displayHosts(hosts);

        view.enterToContinue();
    }

    private void addHost() throws DataException {
        view.displayHeader(HostMenuOption.ADD_HOST.getMessage());

        Host host = view.makeHost();
        Result<Host> result = service.add(host);

        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = "Host added successfully.";
            view.displayStatus(true, successMessage);
            view.displayHostInformation(result.getPayload());
        }

        view.enterToContinue();
    }

    private void editHost() throws DataException {
        view.displayHeader(HostMenuOption.EDIT_HOST.getMessage());

        Host host = helper.getHostByLastName();
        Host updatedHost = view.updateHost(host);
        Result<Host> result = service.update(updatedHost);

        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = "Host updated successfully.";
            view.displayStatus(true, successMessage);
            view.displayHostInformation(result.getPayload());
        }

        view.enterToContinue();
    }

    private void inactivateHost() throws DataException {
        view.displayHeader(HostMenuOption.INACTIVATE_HOST.getMessage());

        Result<Host> result = new Result<>();
        Host host = helper.getHostByLastName();
        if (host == null) {
            result.addErrorMessage("Host not reactivated.");
            view.displayStatus(false, result.getErrorMessages());
            view.enterToContinue();
            return;
        }

        view.displayHostInformation(host);

        boolean inactivateHost = view.confirmHostInactivation();
        if (inactivateHost) {
            result = service.delete(host);
            if (result.isSuccess()) {
                String successMessage = "Host inactivated successfully.";
                view.displayStatus(true, successMessage);
            } else {
                view.displayStatus(false, result.getErrorMessages());
            }
        } else {
            result.addErrorMessage("Host not inactivated.");
            view.displayStatus(false, result.getErrorMessages());
        }

        view.enterToContinue();
    }

    private void reactivateHost() throws DataException {
        view.displayHeader(HostMenuOption.REACTIVATE_HOST.getMessage());

        Result<Host> result = new Result<>();
        Host host = helper.getInactiveHostByLastName();

        if (host == null) {
            result.addErrorMessage("Exiting.");
            view.displayStatus(false, result.getErrorMessages());
            view.enterToContinue();
            return;
        }

        view.displayHostInformation(host);

        boolean reactivateHost = view.confirmHostReactivation();
        if (reactivateHost) {
            result = service.add(host);
            if (result.isSuccess()) {
                String successMessage = "Host reactivated successfully.";
                view.displayStatus(true, successMessage);
            } else {
                view.displayStatus(false, result.getErrorMessages());
            }
        } else {
            result.addErrorMessage("Host not reactivated.");
            view.displayStatus(false, result.getErrorMessages());
        }

        view.enterToContinue();
    }

}
