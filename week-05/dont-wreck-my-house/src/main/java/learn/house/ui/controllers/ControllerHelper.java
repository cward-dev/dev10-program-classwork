package learn.house.ui.controllers;

import learn.house.domain.GuestService;
import learn.house.domain.HostService;
import learn.house.domain.ReservationService;
import learn.house.domain.Result;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import learn.house.ui.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ControllerHelper {

    @Autowired
    private final ReservationService reservationService;

    @Autowired
    private final HostService hostService;

    @Autowired
    private final GuestService guestService;

    @Autowired
    private final View view;

    @Autowired
    public ControllerHelper(ReservationService service, HostService hostService, GuestService guestService, View view) {
        this.reservationService = service;
        this.hostService = hostService;
        this.guestService = guestService;
        this.view = view;
    }

    public Result<Reservation> getReservationByHostLastName() {
        Result<Reservation> result = new Result<>();

        Host host = getHostByLastName();

        List<Reservation> reservations = reservationService.findByHost(host);
        if (reservations.size() == 0) {
            result.addErrorMessage(String.format("No reservations under Host %s (%s).",
                    host.getLastName(),
                    host.getEmail()));
            return result;
        }
        result.setPayload(view.chooseReservation(reservations, host));

        return result;
    }

    public Host getHostByLastName() {
        String hostLastName;
        List<Host> hosts;

        do {
            hostLastName = view.getStringValue("Host Last Name");
            hosts = hostService.findByLastName(hostLastName);

            if (hosts.size() == 0) {
                view.displayStatus(false, String.format("No hosts matching last name '%s'. Please try again.", hostLastName));
            }
        } while (hosts.size() == 0);

        Host host = view.chooseHost(hosts);
        if (host == null) {
            host = getHostByLastName();
            return host;
        }
        return host;
    }

    // Overloaded for editing existing
    public Host getHostByLastName(Host existingHost) {
        String hostLastName;
        List<Host> hosts;

        do {
            hostLastName = view.getStringValue("Host Last Name", existingHost.getLastName());
            if (hostLastName.trim().equalsIgnoreCase(existingHost.getLastName().trim())) {
                return existingHost;
            }

            hosts = hostService.findByLastName(hostLastName);

            if (hosts.size() == 0) {
                view.displayStatus(false, String.format("No hosts matching last name '%s'. Please try again.", hostLastName));
            }
        } while (hosts.size() == 0);

        Host host = view.chooseHost(hosts);
        if (host == null) {
            host = getHostByLastName();
            return host;
        }
        return host;
    }

    public Guest getGuestByLastName() {
        String guestLastName;
        List<Guest> guests;

        do {
            guestLastName = view.getStringValue("Guest Last Name");
            guests = guestService.findByLastName(guestLastName);

            if (guests.size() == 0) {
                view.displayStatus(false, String.format("No guests matching last name '%s'. Please try again.", guestLastName));
            }
        } while (guests.size() == 0);

        Guest guest = view.chooseGuest(guests);
        if (guest == null) {
            guest = getGuestByLastName();
            return guest;
        }
        return guest;
    }

    // Overloaded for editing existing
    public Guest getGuestByLastName(Guest existingGuest) {
        String guestLastName;
        List<Guest> guests;

        do {
            guestLastName = view.getStringValue("Guest Last Name", existingGuest.getLastName());
            if (guestLastName.trim().equalsIgnoreCase(existingGuest.getLastName().trim())) {
                return existingGuest;
            }

            guests = guestService.findByLastName(guestLastName);

            if (guests.size() == 0) {
                view.displayStatus(false, String.format("No guests matching last name '%s'. Please try again.", guestLastName));
            }
        } while (guests.size() == 0);

        Guest guest = view.chooseGuest(guests);
        if (guest == null) {
            guest = getGuestByLastName();
            return guest;
        }
        return guest;
    }
}
