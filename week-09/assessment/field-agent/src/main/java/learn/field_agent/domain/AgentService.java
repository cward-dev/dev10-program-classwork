package learn.field_agent.domain;

import learn.field_agent.data.AgentRepository;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
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

        agent = trimWhitespace(agent);

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

        agent = trimWhitespace(agent);

        if (!repository.update(agent)) {
            String msg = String.format("agentId: %s, not found", agent.getAgentId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int agentId) {
        return repository.deleteById(agentId);
    }

    public Agent makeRandomAgent() {
        List<String> firstNames = List.of("Adam", "Alex", "Aaron", "Ben", "Carl",
                "Dan", "David", "Edward", "Fred", "Frank",
                "George", "Hal", "Hank", "Ike", "John",
                "Jack", "Joe", "Larry", "Monte", "Matthew",
                "Mark", "Nathan", "Otto", "Paul", "Peter",
                "Roger", "Steve", "Thomas", "Tim", "Ty",
                "Victor", "Walter", "Emily", "Hannah", "Madison",
                "Ashley", "Sarah", "Alexis", "Samantha", "Jessica",
                "Elizabeth", "Taylor", "Lauren", "Alyssa", "Kayla",
                "Abigail", "Brianna", "Olivia", "Emma", "Megan",
                "Grace", "Victoria", "Rachel", "Anna", "Sydney",
                "Destiny", "Morgan", "Jennifer", "Jasmine", "Haley",
                "Julia", "Kaitlyn", "Nicole", "Amanda", "Katherine");

        List<String> middleNames = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");

        List<String> lastNames = List.of("Warren", "Jefferson", "Montgomery", "Alpine", "Cobbler",
                "Warden", "Davenport", "Ramish", "Stephenson", "Anderson",
                "Ashwoon", "Aikin", "Bateman", "Bongard", "Bowers",
                "Boyd", "Cannon", "Cast", "Deitz", "Dewalt",
                "Ebner", "Frick", "Hancock", "Haworth", "Hesch",
                "Hoffman", "Kassing", "Knutson", "Lawless", "Lawicki",
                "Mccord", "McCormack", "Miller", "Myers", "Nugent",
                "Ortiz", "Orwig", "Ory", "Paiser", "Pak",
                "Pettigrew", "Quinn", "Quizoz", "Ramachandran", "Resnick",
                "Sagar", "Schickowski", "Schiebel", "Sellon", "Severson", "Shaffer",
                "Solberg", "Soloman", "Sonderling", "Soukup", "Soulis", "Stahl",
                "Sweeney", "Tandy", "Trebil", "Trusela", "Trussel", "Turco", "Uddin",
                "Uflan", "Ulrich", "Upson", "Vader", "Vail", "Valente", "Van Zandt",
                "Vanderpoel", "Ventotla", "Vogal", "Wagle", "Wagner", "Wakefield", "Weinstein",
                "Weiss", "Woo", "Yang", "Yates", "Yocum", "Zeaser", "Zeller", "Ziegler", "Bauer",
                "Baxster", "Casal", "Cataldi", "Caswell", "Celedon", "Chambers", "Chapman",
                "Christensen", "Darnell", "Davidson", "Davis", "DeLorenzo", "Dinkins",
                "Doran", "Dugelman", "Dugan", "Duffman", "Eastman", "Ferro", "Ferry",
                "Fletcher", "Fietzer", "Hylan", "Hydinger", "Illingsworth", "Ingram",
                "Irwin", "Jagtap", "Jenson", "Johnson", "Johnsen", "Jones", "Jurgenson",
                "Kalleg", "Kaskel", "Keller", "Leisinger", "LePage", "Lewis", "Linde",
                "Lulloff", "Maki", "Martin", "McGinnis", "Mills", "Moody", "Moore",
                "Napier", "Nelson", "Norquist", "Nuttle", "Olson", "Ostrander", "Reamer",
                "Reardon", "Reyes", "Rice", "Ripka", "Roberts", "Rogers", "Root",
                "Sandstrom", "Sawyer", "Schlicht", "Schmitt", "Schwager", "Schutz",
                "Schuster", "Tapia", "Thompson", "Tiernan", "Tisler");

        int yearOfBirth = getRandomInt(LocalDate.now().minusYears(62).getYear(),
                LocalDate.now().minusYears(12).getYear());
        int monthOfBirth = getRandomInt(1, 12);
        int dayOfBirth = getRandomInt(1, Month.of(monthOfBirth).length(false)); // No leap year babies :(

        Agent agent = new Agent();
        agent.setFirstName(firstNames.get(getRandomInt(0,firstNames.size() - 1)));
        agent.setMiddleName(middleNames.get(getRandomInt(0, middleNames.size() - 1)));
        agent.setLastName(lastNames.get(getRandomInt(0, lastNames.size() - 1)));
        agent.setDob(LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth));
        agent.setHeightInInches(getRandomInt(36, 96));

        return agent;
    }

    private int getRandomInt(int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
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

    private Agent trimWhitespace(Agent agent) {
        Agent result = agent;
        result.setFirstName(result.getFirstName().trim().replaceAll("\\s+", " "));

        if (result.getMiddleName() != null && result.getMiddleName().length() > 0) {
            result.setMiddleName(result.getMiddleName().trim().replaceAll("\\s+", " "));
        }

        result.setLastName(result.getLastName().trim().replaceAll("\\s+", " "));

        return result;
    }
}
