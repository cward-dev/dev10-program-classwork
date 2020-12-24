package learn.unexplained.data;

import learn.unexplained.domain.EncounterResult;
import learn.unexplained.models.Encounter;
import learn.unexplained.models.EncounterType;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class EncounterRepositoryDouble implements EncounterRepository {

    private ArrayList<Encounter> encounters = new ArrayList<>();

    public EncounterRepositoryDouble() {
        encounters.add(new Encounter(1,EncounterType.CREATURE,"1/1/2020", "it was scary", 1));
        encounters.add(new Encounter(2,EncounterType.UFO,"1/2/2020", "it was strange", 4));
        encounters.add(new Encounter(3,EncounterType.SOUND,"1/3/2020", "it was loud", 2));
        encounters.add(new Encounter(4,EncounterType.SOUND,"1/4/2020", "it was big", 5));
    }

    @Override
    public List<Encounter> findAll() throws DataAccessException {
        return encounters;
    }

    @Override
    public Encounter findById(int encounterId) throws DataAccessException {
        for (Encounter e : encounters) {
            if (e.getEncounterId() == encounterId) {
                return e;
            }
        }
        return null;
    }

    @Override
    public List<Encounter> findByType(EncounterType encounterType) throws DataAccessException {
        List<Encounter> encounters = findAll();

        ArrayList<Encounter> result = new ArrayList<>();
        for (Encounter e : encounters) {
            if (e.getType() == encounterType) {
                result.add(e);
            }
        }
        return result;
    }

    @Override
    public Encounter add(Encounter encounter) throws DataAccessException {
        return encounter;
    }

    @Override
    public boolean update(Encounter encounter) throws DataAccessException {
        for (int i = 0; i < encounters.size(); i++) {
            if (encounters.get(i).getEncounterId() == encounter.getEncounterId()) {
                encounters.remove(i);
                encounters.add(encounter);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int encounterId) throws DataAccessException {
        for (int i = 0; i < encounters.size(); i++) {
            if (encounters.get(i).getEncounterId() == encounterId) {
                encounters.remove(i);
                return true;
            }
        }
        return false;
    }
}
