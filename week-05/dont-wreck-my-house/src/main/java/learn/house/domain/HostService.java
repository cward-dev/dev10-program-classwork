package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.HostRepository;
import learn.house.models.Host;
import learn.house.models.State;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

        boolean success = repository.update(host);
        if (!success) {
            result.addErrorMessage(String.format("Host email '%s' not found.", host.getEmail()));
        }

        result.setPayload(host);
        return result;
    }

    public Result<Host> deleteById(String hostId) throws DataException {
        Host host = findById(hostId);
        Result<Host> result = validate(host);

        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.deleteById(hostId);
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

        if (String.format("%s", host.getPostalCode()).length() != 5) {
            result.addErrorMessage("Host postal code is required and must be a 5 digit number.");
        }

        if (host.getStandardRate() == null || host.getStandardRate().compareTo(new BigDecimal("0.00")) < 0) {
            result.addErrorMessage("Host standard rate is required and must be a positive dollar amount.");
        }

        if (host.getWeekendRate() == null || host.getWeekendRate().compareTo(new BigDecimal("0.00")) < 0) {
            result.addErrorMessage("Host weekend rate is required and must be a positive dollar amount.");
        }

        return result;
    }

    private boolean checkForDuplicate(Host host) {
        return repository.findAll().stream()
                .anyMatch(g -> g.equals(host) && !g.getId().equals(host.getId()));
    }
}
