package learn.field_agent.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class Alias {

    int aliasId;

    @NotBlank(message = "Alias name is required.")
    String name;

    String persona;

    @Min(value = 1, message = "Alias must be assigned to an Agent ID.")
    int agentId;

    public int getAliasId() {
        return aliasId;
    }

    public void setAliasId(int aliasId) {
        this.aliasId = aliasId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public Alias() {
    }

    public Alias(int aliasId, String name, String persona, int agentId) {
        this.aliasId = aliasId;
        this.name = name;
        this.persona = persona;
        this.agentId = agentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alias that = (Alias) o;
        return aliasId == that.aliasId &&
                Objects.equals(name, that.name) &&
                Objects.equals(persona, that.persona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aliasId, name);
    }
}
