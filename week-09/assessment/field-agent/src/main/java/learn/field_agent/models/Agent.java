package learn.field_agent.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Agent {

    private int agentId;

    @NotBlank(message = "Agent first name is required.")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Agent last name is required.")
    private String lastName;

    @NotNull(message = "Agent date of birth is required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth must be in the past.")
    private LocalDate dob;

    @Min(value = 36, message = "Agent height must be between 36 and 96 inches.")
    @Max(value = 96, message = "Agent height must be between 36 and 96 inches.")
    private int heightInInches;

    private List<AgentAgency> agencies = new ArrayList<>();
    private List<Alias> aliases = new ArrayList<>();

    public Agent() {
    }

    public Agent(int agentId, String firstName, String middleName, String lastName, LocalDate dob, int heightInInches) {
        this.agentId = agentId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dob = dob;
        this.heightInInches = heightInInches;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public int getHeightInInches() {
        return heightInInches;
    }

    public void setHeightInInches(int heightInInches) {
        this.heightInInches = heightInInches;
    }

    public List<AgentAgency> getAgencies() {
        return new ArrayList<>(agencies);
    }

    public void setAgencies(List<AgentAgency> agencies) {
        this.agencies = agencies;
    }

    public List<Alias> getAliases() {
        return aliases;
    }

    public void setAliases(List<Alias> aliases) {
        this.aliases = aliases;
    }
}
