package learn.unexplained.data;

import learn.unexplained.models.Encounter;
import learn.unexplained.models.EncounterType;

import javax.xml.crypto.Data;
import java.util.List;

public interface EncounterRepository {
    List<Encounter> findAll() throws DataAccessException;

    Encounter findById(int encounterId) throws DataAccessException;

    List<Encounter> findByType(EncounterType encounterType) throws DataAccessException;

    Encounter add(Encounter encounter) throws DataAccessException;

    boolean update(Encounter encounter) throws  DataAccessException;

    boolean deleteById(int encounterId) throws DataAccessException;
}
