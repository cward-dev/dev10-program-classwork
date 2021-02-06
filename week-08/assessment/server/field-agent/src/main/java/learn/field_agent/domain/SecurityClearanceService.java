package learn.field_agent.domain;

import learn.field_agent.data.AgencyAgentRepository;
import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.SecurityClearance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityClearanceService {

    private final SecurityClearanceRepository repository;

    private final AgencyAgentRepository agencyAgentRepository;

    public SecurityClearanceService(SecurityClearanceRepository repository, AgencyAgentRepository agencyAgentRepository) {
        this.repository = repository;
        this.agencyAgentRepository = agencyAgentRepository;
    }

    public List<SecurityClearance> findAll() { return repository.findAll(); }

    public SecurityClearance findById(int securityClearanceId) { return repository.findById(securityClearanceId); }

    public Result<SecurityClearance> add(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = validate(securityClearance);
        if (!result.isSuccess()) {
            return result;
        }

        if (securityClearance.getSecurityClearanceId() != 0) {
            result.addMessage("securityClearanceId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        securityClearance = repository.add(securityClearance);
        result.setPayload(securityClearance);
        return result;
    }

    public Result<SecurityClearance> update(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = validate(securityClearance);
        if (!result.isSuccess()) {
            return result;
        }

        if (securityClearance.getSecurityClearanceId() <= 0) {
            result.addMessage("securityClearanceId must be set be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(securityClearance)) {
            String msg = String.format("securityClearanceId: %s, not found", securityClearance.getSecurityClearanceId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<SecurityClearance> deleteById(int securityClearanceId) {
        Result<SecurityClearance> result = new Result<>();

        boolean dependentAgencyAgentExists = checkForDependentAgencyAgent(securityClearanceId);
        if (dependentAgencyAgentExists) {
            result.addMessage("cannot delete a securityClearance with dependent agencyAgent", ResultType.INVALID);
            return result;
        }

        boolean deleted = repository.deleteById(securityClearanceId);
        if (!deleted) {
            String msg = String.format("securityClearanceId: %s, not found", securityClearanceId);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<SecurityClearance> validate(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = new Result<>();
        if (securityClearance == null) {
            result.addMessage("securityClearance cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(securityClearance.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }

        if (checkForDuplicate(securityClearance)) {
            String msg = String.format("name: '%s', already exists", securityClearance.getName());
            result.addMessage(msg, ResultType.INVALID);
        }

        return result;
    }

    private boolean checkForDuplicate(SecurityClearance securityClearance) {
        return findAll().stream()
                .anyMatch(sc -> sc.getName().equalsIgnoreCase(securityClearance.getName())
                        && sc.getSecurityClearanceId() != securityClearance.getSecurityClearanceId());
    }

    private boolean checkForDependentAgencyAgent(int securityClearanceId) {
        return agencyAgentRepository.findBySecurityClearanceId(securityClearanceId).size() > 0;
    }
}
