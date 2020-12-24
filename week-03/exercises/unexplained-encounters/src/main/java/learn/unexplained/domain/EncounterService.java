package learn.unexplained.domain;

import learn.unexplained.data.DataAccessException;
import learn.unexplained.data.EncounterRepository;
import learn.unexplained.models.Encounter;
import learn.unexplained.models.EncounterType;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EncounterService {

    private final EncounterRepository repository;

    public EncounterService(EncounterRepository repository) {
        this.repository = repository;
    }

    public List<Encounter> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public Encounter findById(int encounterId) throws DataAccessException {
        return repository.findById(encounterId);
    }

    public List<Encounter> findByType(EncounterType type) throws DataAccessException {
        return repository.findByType(type);
    }

    public EncounterResult add(Encounter encounter) throws DataAccessException {
        EncounterResult result = validate(encounter);
        if (!result.isSuccess()) {
            return result;
        }

        boolean duplicate = checkForDuplicate(encounter);
        if (duplicate) {
            result.addErrorMessage("duplicate encounter is not allowed");
            return result;
        }

        encounter = repository.add(encounter);
        result.setPayload(encounter);
        return result;
    }

    public EncounterResult update(Encounter encounter) throws DataAccessException {
        EncounterResult result = validate(encounter);
        if (!result.isSuccess()) {
            return result;
        }

        boolean duplicate = checkForDuplicate(encounter);
        if (duplicate) {
            result.addErrorMessage("duplicate encounter is not allowed");
            return result;
        }

        boolean success = repository.update(encounter);
        if (!success) {
            result.addErrorMessage("Encounter Id " + encounter.getEncounterId() + " not found.");
        }

        return result;
    }

    public EncounterResult deleteById(int encounterId) throws DataAccessException {
        Encounter encounter = findById(encounterId);
        EncounterResult result = validate(encounter);
        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.deleteById(encounterId);
        if (!success) {
            result.addErrorMessage("Encounter Id " + encounterId + " not found.");
        }

        return result;
    }

    private EncounterResult validate(Encounter encounter) {
        EncounterResult result = new EncounterResult();
        if (encounter == null) {
            result.addErrorMessage("encounter cannot be null");
            return result;
        }

        if (encounter.getWhen() == null || encounter.getWhen().trim().length() == 0) {
            result.addErrorMessage("when is required");
        }

        if (encounter.getDescription() == null || encounter.getDescription().trim().length() == 0) {
            result.addErrorMessage("description is required");
        }

        if (encounter.getOccurrences() <= 0) {
            result.addErrorMessage("occurrences must be greater than 0");
        }

        return result;
    }

    private boolean checkForDuplicate(Encounter encounter) throws DataAccessException {
        EncounterResult result = validate(encounter);

        List<Encounter> encounters = findAll();
        for (Encounter e : encounters) {
            if (Objects.equals(encounter.getWhen(), e.getWhen())
                    && Objects.equals(encounter.getType(), e.getType())
                    && Objects.equals(encounter.getDescription(), e.getDescription())) {
                return true;
            }
        }

        return false;
    }
}
