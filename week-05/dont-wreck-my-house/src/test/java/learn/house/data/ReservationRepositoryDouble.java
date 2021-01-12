package learn.house.data;

import learn.house.models.Host;
import learn.house.models.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository {

    List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findByHost(Host host) {
        return reservations.stream()
                .filter(reservation -> reservation.getHost().equals(host))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {

        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId() == reservation.getId()) {
                reservations.set(i, reservation);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(Reservation reservation) throws DataException {

        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId() == reservation.getId()) {
                reservations.remove(i);
                return true;
            }
        }

        return false;
    }
}
