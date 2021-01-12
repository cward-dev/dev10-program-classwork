package learn.house.data;

import learn.house.models.Host;
import learn.house.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findByHost(Host host);

    Reservation add(Host host, Reservation reservation) throws DataException;

    boolean update(Host host, Reservation reservation) throws DataException;

    boolean delete(Host host, Reservation reservation) throws DataException;
}