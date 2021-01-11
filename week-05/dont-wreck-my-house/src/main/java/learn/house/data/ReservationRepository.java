package learn.house.data;

import learn.house.models.Host;
import learn.house.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll(); // Stretch goal

    List<Reservation> findByHost(Host host);

    Reservation findById(String id); // Stretch goal

    Reservation add(Reservation reservation) throws DataException;

    boolean update(Reservation reservation) throws DataException;

    boolean delete(Reservation reservation) throws DataException;
}