package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.HostRepository;
import learn.house.models.Host;
import learn.house.models.State;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HostService {

    private final HostRepository repository;

    public HostService(HostRepository repository) { this.repository = repository; }

    public List<Host> findAll() {
        return repository.findAll();
    }

    public List<Host> findByState(State state) {
        return repository.findAll().stream()
                .filter(host -> host.getState() == state)
                .collect(Collectors.toList());
    }

    public List<Host> findByLastName(String lastName) {
        return repository.findAll().stream()
                .filter(h -> h.getLastName().trim()
                        .substring(0,Math.min(h.getLastName().trim().length(), lastName.trim().length()))
                        .equalsIgnoreCase(lastName.trim()))
                .collect(Collectors.toList());
    }

    public Host findById(String hostId) {
        return repository.findById(hostId);
    }

    public Host findByEmail(String hostEmail) {
        return repository.findByEmail(hostEmail);
    }

    public Result<Host> add(Host host) throws DataException {
        Result<Host> result = validate(host);

        if (!result.isSuccess()) {
            return result;
        }

        boolean isDuplicate = checkForDuplicate(host);
        if (isDuplicate) {
            result.addErrorMessage(String.format("Host email '%s' is already in use.",
                    host.getEmail()));
            return result;
        }

        result.setPayload(repository.add(host));
        return result;
    }

    public Result<Host> update(Host host) throws DataException {
        Result<Host> result = validate(host);

        if (!result.isSuccess()) {
            return result;
        }

        boolean isDuplicate = checkForDuplicate(host);
        if (isDuplicate) {
            result.addErrorMessage(String.format("Host email '%s' is already in use.",
                    host.getEmail()));
            return result;
        }

        boolean emailIsTakenByInactive = checkEmailBelongsToDeletedHost(host.getEmail());
        if (emailIsTakenByInactive) {
            result.addErrorMessage(String.format("An inactive host shares the email '%s'.%nIf you wish to use this email, you must make a new Host.",
                    host.getEmail()));
        }

        boolean success = repository.update(host);
        if (!success) {
            result.addErrorMessage(String.format("Host email '%s' not found.", host.getEmail()));
        }

        result.setPayload(host);
        return result;
    }

    public Result<Host> delete(Host host) throws DataException {
        Result<Host> result = validate(host);

        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.delete(host);
        if (!success) {
            result.addErrorMessage(String.format("Host email '%s' not found.", host.getEmail()));
        }

        result.setPayload(host);
        return result;
    }

    private Result<Host> validate(Host host) {
        Result<Host> result = new Result<>();

        if (host == null) {
            result.addErrorMessage("No host to save.");
            return result;
        }

        if (host.getLastName() == null || host.getLastName().trim().length() == 0) {
            result.addErrorMessage("Host last name is required.");
        }

        if (host.getEmail() == null || host.getEmail().trim().length() == 0) {
            result.addErrorMessage("Host email is required.");
        }

        if (host.getPhone() == null || host.getPhone().trim().length() == 0) {
            result.addErrorMessage("Host phone is required.");
        }

        if (host.getAddress() == null || host.getAddress().trim().length() == 0) {
            result.addErrorMessage("Host address is required.");
        }

        if (host.getCity() == null || host.getCity().trim().length() == 0) {
            result.addErrorMessage("Host city is required.");
        }

        if (host.getState() == null) {
            result.addErrorMessage("Host state is required.");
        }

        if (host.getPostalCode() == null || host.getPostalCode().length() == 0) {
            result.addErrorMessage("Host postal code is required.");
        }

        if (host.getStandardRate() == null || host.getStandardRate().compareTo(new BigDecimal("0.00")) < 0) {
            result.addErrorMessage("Host standard rate is required and must be a positive dollar amount.");
        }

        if (host.getWeekendRate() == null || host.getWeekendRate().compareTo(new BigDecimal("0.00")) < 0) {
            result.addErrorMessage("Host weekend rate is required and must be a positive dollar amount.");
        }

        return result;
    }

    private boolean checkEmailBelongsToDeletedHost(String email) {
        return repository.findDeletedByEmail(email) != null;
    }

    private boolean checkForDuplicate(Host host) {
        return repository.findAll().stream()
                .anyMatch(g -> g.equals(host) && !g.getId().equals(host.getId()));
    }
}
