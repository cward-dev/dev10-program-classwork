package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import learn.house.models.State;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    private final LocalDate START_DATE = LocalDate.now().plusDays(5);
    private final LocalDate END_DATE = START_DATE.plusDays(5);

    private final Host HOST = new Host("3edda6bc-ab95-49a8-8962-d50b53f84b15",
            "Yearnes",
            "eyearnes0@sfgate.com",
            "(806) 1783815",
            "3 Nova Trail", "Amarillo", State.TEXAS, 79182,
            new BigDecimal("340"),
            new BigDecimal("425"));

    private final Guest GUEST = new Guest(1, "Sullivan", "Lomas",
            "slomas0@mediafire.com", "(702) 7768761",State.NEVADA);

    public ReservationRepositoryDouble() throws IOException {
        reservations.add(new Reservation(1,
                START_DATE,
                END_DATE,
                HOST,
                GUEST,
                new BigDecimal("360")));
    }

    @Override
    public List<Reservation> findByHost(Host host) {
        if (host == null) {
            return null;
        }

        return reservations.stream()
                .filter(r -> r.getHost().equals(host))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        if (reservation == null || reservation.getHost() == null) {
            return null;
        }
        int nextId = reservations.stream()
                .mapToInt(Reservation::getId)
                .max()
                .orElse(0) + 1;;
        reservation.setId(nextId);

        reservations.add(reservation);
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {

        if (reservation == null || reservation.getHost() == null) {
            return false;
        }

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

        if (reservation == null || reservation.getHost() == null) {
            return false;
        }

        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId() == reservation.getId()) {
                reservations.remove(i);
                return true;
            }
        }

        return false;
    }
}
