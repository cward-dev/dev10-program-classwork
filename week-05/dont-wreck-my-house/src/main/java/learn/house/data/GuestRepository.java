package learn.house.data;

import learn.house.models.Guest;

import java.util.List;

public interface GuestRepository {

    List<Guest> findAll();

    Guest findById(int id);

    List<Guest> findAllDeleted();

    Guest findDeletedById(int id);

    Guest findByEmail(String email);

    Guest add(Guest guest) throws DataException;

    boolean update(Guest guest) throws DataException;

    boolean deleteById(int id) throws DataException;
}
