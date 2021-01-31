package learn.field_agent.domain;

import learn.field_agent.data.AgentRepository;
import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AliasService {

    private final AliasRepository repository;

    private final AgentRepository agentRepository;

    public AliasService(AliasRepository repository, AgentRepository agentRepository) {
        this.repository = repository;
        this.agentRepository = agentRepository;
    }

    public List<Alias> findAll() {
        return repository.findAll();
    }

    public List<Alias> findByAgentId(int agentId) {
        return repository.findByAgentId(agentId);
    }

    public Alias findById(int aliasId) {
        return repository.findById(aliasId);
    }

    public Result<Alias> add(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() != 0) {
            result.addMessage("aliasId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        alias = repository.add(alias);
        result.setPayload(alias);
        return result;
    }

    public Result<Alias> update(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() <= 0) {
            result.addMessage("aliasId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(alias)) {
            String msg = String.format("aliasId: %s, not found", alias.getAliasId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int aliasId) {
        return repository.deleteById(aliasId);
    }

    private Result<Alias> validate(Alias alias) {
        Result<Alias> result = new Result<>();

        if (alias == null) {
            result.addMessage("alias cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(alias.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(alias.getPersona())) { // If empty, make persona null
            alias.setPersona(null);
        }

        if (checkForDuplicate(alias)) {
            String msg;
            if (alias.getPersona() == null) {
                msg = String.format("name: '%s', already exists, cannot set without persona", alias.getName());
                result.addMessage(msg, ResultType.INVALID);
            } else {
                if (checkForDuplicate(alias)) {
                    msg = String.format("name: '%s', persona: '%s', already exists", alias.getName(), alias.getPersona());
                    result.addMessage(msg, ResultType.INVALID);
                }
            }
        }

        if (!checkForValidAgentId(alias.getAgentId())) {
            String msg = String.format("agentId: %s, not found", alias.getAgentId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    private boolean checkForDuplicate(Alias alias) {
        if (alias.getPersona() == null) {
            return findAll().stream()
                    .filter(a -> a.getName().equalsIgnoreCase(alias.getName()))
                    .anyMatch(a -> a.getPersona() == null
                            && a.getAliasId() != alias.getAliasId());
        }
        return findAll().stream()
                .filter(a -> a.getName().equalsIgnoreCase(alias.getName()))
                .filter(a -> a.getPersona() != null)
                .anyMatch(a -> a.getPersona().equalsIgnoreCase(alias.getPersona())
                        && a.getAliasId() != alias.getAliasId());

    }

    private boolean checkForValidAgentId(int agentId) {
        return agentRepository.findById(agentId) != null;
    }
}
