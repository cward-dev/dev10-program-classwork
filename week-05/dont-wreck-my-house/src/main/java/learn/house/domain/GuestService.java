package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepository;
import learn.house.models.Guest;
import learn.house.models.State;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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

    public List<Guest> findByLastName(String lastName) {
        return repository.findAll().stream()
                .filter(h -> h.getLastName().trim()
                        .substring(0,Math.min(h.getLastName().trim().length(), lastName.trim().length()))
                        .equalsIgnoreCase(lastName.trim()))
                .collect(Collectors.toList());
    }

    public Guest findById(int guestId) {
        return repository.findById(guestId);
    }

    public Guest findByEmail(String guestEmail) {
        return repository.findByEmail(guestEmail);
    }

    public List<Guest> findAllDeleted() {
        return repository.findAllDeleted();
    }

    public List<Guest> findDeletedByLastName(String lastName) {
        return repository.findAllDeleted().stream()
                .filter(g -> g.getLastName().trim()
                        .substring(0,Math.min(g.getLastName().trim().length(), lastName.trim().length()))
                        .equalsIgnoreCase(lastName.trim()))
                .collect(Collectors.toList());    }

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

        boolean emailIsTakenByInactive = checkEmailBelongsToDeletedGuest(guest.getEmail());
        if (emailIsTakenByInactive) {
            result.addErrorMessage(String.format("An inactive host shares the email '%s'.%nIf you wish to use this email, you must make a new Host.",
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

    public Result<Guest> delete(Guest guest) throws DataException {
        Result<Guest> result = validate(guest);

        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.delete(guest);
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
        if (guest.getFirstName().charAt(0) == '/') result.addErrorMessage("Guest first name cannot begin with '/' command symbol.");

        if (guest.getLastName() == null || guest.getLastName().trim().length() == 0) {
            result.addErrorMessage("Guest last name is required.");
        }
        if (guest.getLastName().charAt(0) == '/') result.addErrorMessage("Guest last name cannot begin with '/' command symbol.");

        if (guest.getEmail() == null || guest.getEmail().trim().length() == 0) {
            result.addErrorMessage("Guest email is required.");
        }
        if (guest.getEmail().charAt(0) == '/') result.addErrorMessage("Guest email cannot begin with '/' command symbol.");

        if (guest.getPhone() == null || guest.getPhone().trim().length() == 0) {
            result.addErrorMessage("Guest phone is required.");
        }
        if (guest.getPhone().charAt(0) == '/') result.addErrorMessage("Guest phone cannot begin with '/' command symbol.");

        if (guest.getState() == null) {
            result.addErrorMessage("Guest state is required.");
        }

        return result;
    }

    private boolean checkEmailBelongsToDeletedGuest(String email) {
        return repository.findDeletedByEmail(email) != null;
    }

    private boolean checkForDuplicate(Guest guest) {
        return repository.findAll().stream()
                .anyMatch(g -> g.equals(guest) && g.getId() != guest.getId());
    }
}
