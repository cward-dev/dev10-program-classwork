package learn.field_agent.domain;

import learn.field_agent.data.AgentRepository;
import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class AliasService {

    private final AliasRepository repository;

    private final AgentRepository agentRepository;

    Validator validator;

    public AliasService(AliasRepository repository, AgentRepository agentRepository) {
        this.repository = repository;
        this.agentRepository = agentRepository;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
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
            result.addMessage("Alias ID cannot be set for `add` operation.", ResultType.INVALID);
            return result;
        }

        alias = trimWhitespace(alias);

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
            result.addMessage("Alias ID must be set for `update` operation.", ResultType.INVALID);
            return result;
        }

        alias = trimWhitespace(alias);

        if (!repository.update(alias)) {
            String msg = String.format("Alias ID: %s, not found.", alias.getAliasId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int aliasId) {
        return repository.deleteById(aliasId);
    }

    public Alias makeRandomAlias(int agentId) {
        List<String> names = List.of("Willow", "Robinhood", "Pickle", "Roofie", "Big Ben", "Limon",
                "Janitor", "Arkham", "Tailwind", "Sailor", "Racket", "Flyaway", "Roundhouse", "Aether",
                "Market", "Lobster", "Timeshare", "Bowler", "Bowser", "Sydney", "Tapdance", "Mana",
                "Tomcat", "Penny", "Nickel", "Sous Vide", "Broiler", "Sugar", "Madison", "Anchorman",
                "Zoolander", "Rochester", "Gangnam", "Opa", "Samsung", "Apple", "Tuesday", "Yellow Bus",
                "Freight Train", "Snake Eyes", "Double Blind", "Twisty Straw", "Sumpin Sumpin", "Big Bertha");
        List<String> personas = List.of("Z-3", "009", "RN", "DQ", "10", "M7", "PU", "4K", "DD", "REM", "EVIL",
                "6242", "C-3PO", "R2-D2", "The Wily One", "U2", "TB12", "TTYL", "BRB", "LOL");

        Alias alias = new Alias();
        alias.setName(names.get((int)(Math.random() * names.size())));
        alias.setPersona(personas.get((int)(Math.random() * personas.size())));
        alias.setAgentId(agentId);

        return alias;
    }

    private Result<Alias> validate(Alias alias) {
        Result<Alias> result = new Result<>();

        if (alias == null) {
            result.addMessage("Alias cannot be null.", ResultType.INVALID);
            return result;
        }

        Set<ConstraintViolation<Alias>> violations = validator.validate(alias); // Kept in validate() method because we're manually checking for duplicate/valid agentId anyway

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Alias> violation : violations) {
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
        }

        if (Validations.isNullOrBlank(alias.getPersona())) { // If empty, make persona null for duplicate check
            alias.setPersona(null);
        }

        if (checkForDuplicate(alias)) {
            String msg;
            if (alias.getPersona() == null) {
                msg = String.format("Name: '%s', already exists, cannot set without persona.", alias.getName());
            } else {
                msg = String.format("Name: '%s', Persona: '%s', already exists.", alias.getName(), alias.getPersona());
            }
            result.addMessage(msg, ResultType.INVALID);
        }

        if (!checkForValidAgentId(alias.getAgentId())) {
            String msg = String.format("Agent ID: %s, not found.", alias.getAgentId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    private boolean checkForDuplicate(Alias alias) {
        if (alias.getName() == null) {
            return false;
        }
        if (alias.getPersona() == null) {
            return findAll().stream()
                    .filter(a -> a.getName().trim().equalsIgnoreCase(alias.getName().trim()))
                    .anyMatch(a -> a.getPersona() == null
                            && a.getAliasId() != alias.getAliasId());
        }
        return findAll().stream()
                .filter(a -> a.getName().trim().equalsIgnoreCase(alias.getName().trim()))
                .filter(a -> a.getPersona() != null)
                .anyMatch(a -> a.getPersona().trim().equalsIgnoreCase(alias.getPersona().trim())
                        && a.getAliasId() != alias.getAliasId());

    }

    private boolean checkForValidAgentId(int agentId) {
        return agentRepository.findById(agentId) != null;
    }

    private Alias trimWhitespace(Alias alias) { // Clean up whitespace in String fields
        Alias result = alias;
        result.setName(result.getName().trim().replaceAll("\\s+", " "));

        if (result.getPersona() != null && result.getPersona().length() > 0) {
            result.setPersona(result.getPersona().trim().replaceAll("\\s+", " "));
        }

        return result;
    }
}
