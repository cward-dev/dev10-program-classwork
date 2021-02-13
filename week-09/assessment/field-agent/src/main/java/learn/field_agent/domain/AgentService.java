package learn.field_agent.domain;

import learn.field_agent.data.AgentRepository;
import learn.field_agent.models.Agent;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class AgentService {

    private final AgentRepository repository;

    Validator validator;

    public AgentService(AgentRepository repository) {
        this.repository = repository;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public List<Agent> findAll() {
        return repository.findAll();
    }

    public Agent findById(int agentId) {
        return repository.findById(agentId);
    }

    public Result<Agent> add(Agent agent) {
        Result<Agent> result = validate(agent);
        if (!result.isSuccess()) {
            return result;
        }

        if (agent.getAgentId() != 0) {
            result.addMessage("Agent ID cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        agent = repository.add(agent);
        result.setPayload(agent);
        return result;
    }

    public Result<Agent> update(Agent agent) {
        Result<Agent> result = validate(agent);
        if (!result.isSuccess()) {
            return result;
        }

        if (agent.getAgentId() <= 0) {
            result.addMessage("Agent ID must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(agent)) {
            String msg = String.format("agentId: %s, not found", agent.getAgentId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int agentId) {
        return repository.deleteById(agentId);
    }

    private Result<Agent> validate(Agent agent) {
        Result<Agent> result = new Result<>();

        if (agent == null) {
            result.addMessage("Agent cannot be null.", ResultType.INVALID);
            return result;
        }

        Set<ConstraintViolation<Agent>> violations = validator.validate(agent);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Agent> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }

        if (agent.getDob() != null && agent.getDob().isAfter(LocalDate.now().minusYears(12))) {
            result.addMessage("Agents younger than 12 are not allowed.", ResultType.INVALID);
        }

        return result;
    }
}
