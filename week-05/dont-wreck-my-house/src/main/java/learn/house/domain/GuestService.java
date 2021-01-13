package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepository;
import learn.house.models.Guest;
import learn.house.models.State;

import java.util.List;
import java.util.stream.Collectors;

public class GuestService {

    private final GuestRepository repository;

    public GuestService(GuestRepository repository) { this.repository = repository; }

    public List<Guest> findAll() {
        return repository.findAll();
    }

    public List<Guest> findByState(State state) {
        return repository.findAll().stream()
                .filter(guest -> guest.getState() == state)
                .collect(Collectors.toList());
    }

    public Guest findById(int guestId) {
        return repository.findById(guestId);
    }

    public Guest findByEmail(String guestEmail) {
        return repository.findByEmail(guestEmail);
    }

    public Result<Guest> add(Guest guest) throws DataException {
        Result<Guest> result = validate(guest);

        if (!result.isSuccess()) {
            return result;
        }

        boolean isDuplicate = checkForDuplicate(guest);
        if (isDuplicate) {
            result.addErrorMessage(String.format("Guest email '%s' is already in use.",
                    guest.getEmail()));
            return result;
        }

        result.setPayload(repository.add(guest));
        return result;
    }

    public Result<Guest> update(Guest guest) throws DataException {
        Result<Guest> result = validate(guest);

        if (!result.isSuccess()) {
            return result;
        }

        boolean isDuplicate = checkForDuplicate(guest);
        if (isDuplicate) {
            result.addErrorMessage(String.format("Guest email '%s' is already in use.",
                    guest.getEmail()));
            return result;
        }

        boolean success = repository.update(guest);
        if (!success) {
            result.addErrorMessage(String.format("Guest email '%s' not found.", guest.getEmail()));
        }

        result.setPayload(guest);
        return result;
    }

    public Result<Guest> deleteById(int guestId) throws DataException {
        Guest guest = findById(guestId);
        Result<Guest> result = validate(guest);

        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.deleteById(guestId);
        if (!success) {
            result.addErrorMessage(String.format("Guest email '%s' not found.", guest.getEmail()));
        }

        result.setPayload(guest);
        return result;
    }

    private Result<Guest> validate(Guest guest) {
        Result<Guest> result = new Result<>();

        if (guest == null) {
            result.addErrorMessage("No guest to save.");
            return result;
        }

        if (guest.getFirstName() == null || guest.getFirstName().trim().length() == 0) {
            result.addErrorMessage("Guest first name is required.");
        }

        if (guest.getLastName() == null || guest.getLastName().trim().length() == 0) {
            result.addErrorMessage("Guest last name is required.");
        }

        if (guest.getEmail() == null || guest.getEmail().trim().length() == 0) {
            result.addErrorMessage("Guest email is required.");
        }

        if (guest.getPhone() == null || guest.getPhone().trim().length() == 0) {
            result.addErrorMessage("Guest phone is required.");
        }

        if (guest.getState() == null) {
            result.addErrorMessage("Guest state is required.");
        }

        return result;
    }

    private boolean checkForDuplicate(Guest guest) {
        return repository.findAll().stream()
                .anyMatch(g -> g.equals(guest) && g.getId() != guest.getId());
    }
}
