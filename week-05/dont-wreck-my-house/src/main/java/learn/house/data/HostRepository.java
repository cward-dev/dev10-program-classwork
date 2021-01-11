package learn.house.data;

import learn.house.models.Host;

import java.util.List;

public interface HostRepository {
    List<Host> findAll();

    Host findById(String id);

    Host findByEmail(String email);

    Host add(Host host) throws DataException;

    boolean update(Host host) throws DataException;

    boolean delete(Host host) throws DataException;
}