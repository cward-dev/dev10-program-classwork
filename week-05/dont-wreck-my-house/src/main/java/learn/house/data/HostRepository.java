package learn.house.data;

import learn.house.models.Host;

import java.util.List;

public interface HostRepository {
    List<Host> findAll();

    Host findById(String id);

    List<Host> findAllDeleted();

    Host findDeletedById(String id);

    Host findByEmail(String email);

    Host add(Host host) throws DataException;

    boolean update(Host host) throws DataException;

    boolean deleteById(String id) throws DataException;
}