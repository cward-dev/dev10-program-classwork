package learn.field_agent.models;

import java.util.Objects;

public class Alias {

    int aliasId;
    String name;
    String persona;
    int agent_id;

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

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public Alias() {
    }

    public Alias(int aliasId, String name, String persona) {
        this.aliasId = aliasId;
        this.name = name;
        this.persona = persona;
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
