package learn.foraging.domain;

import learn.foraging.data.DataException;
import learn.foraging.data.ForagerRepository;
import learn.foraging.models.Forage;
import learn.foraging.models.Forager;
import learn.foraging.models.Item;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForagerService {

    private final ForagerRepository repository;

    public ForagerService(ForagerRepository repository) {
        this.repository = repository;
    }

    public List<Forager> findByState(String stateAbbr) {
        return repository.findByState(stateAbbr);
    }

    public List<Forager> findByLastName(String prefix) {
        return repository.findAll().stream()
                .filter(i -> i.getLastName().startsWith(prefix))
                .collect(Collectors.toList());
    }

    public Result<Forager> add(Forager forager) throws DataException {
        Result<Forager> result = validate(forager);
        if (!result.isSuccess()) {
            return result;
        }

        boolean isDuplicate = checkForDuplicate(forager);
        if (isDuplicate) {
            result.addErrorMessage(String.format("Forager %s %s from %s already exists.",
                    forager.getFirstName(), forager.getLastName(), forager.getState().getAbbreviation()));
            return result;
        }

        result.setPayload(repository.add(forager));
        return result;
    }

    public Result<Forager> update(Forager forager) throws DataException {
        Result<Forager> result = validate(forager);
        if (!result.isSuccess()) {
            return result;
        }

        boolean isDuplicate = checkForDuplicate(forager);
        if (isDuplicate) {
            result.addErrorMessage(String.format("Forager %s %s from %s already exists.",
                    forager.getFirstName(), forager.getLastName(), forager.getState().getAbbreviation()));
            return result;
        }

        boolean success = repository.update(forager);
        if (!success) {
            result.addErrorMessage(String.format("Forager Id %s not found.",
                    forager.getId()));
        }

        result.setPayload(forager);
        return result;
    }

    private Result<Forager> validate(Forager forager) {
        Result<Forager> result = new Result<>();

        if (forager == null) {
            result.addErrorMessage("No forager to save.");
            return result;
        }

        if (forager.getFirstName() == null || forager.getFirstName().trim().length() == 0) {
            result.addErrorMessage("Forager first name is required.");
        }

        if (forager.getLastName() == null || forager.getLastName().trim().length() == 0) {
            result.addErrorMessage("Forager last name is required.");
        }

        if (forager.getState() == null) {
            result.addErrorMessage("Forager state is required.");
        }
        return result;
    }

    private boolean checkForDuplicate(Forager forager) {
        List<Forager> all = repository.findAll();

        return all.stream()
                .anyMatch(f -> f.equals(forager));
    }
}
