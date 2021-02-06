package learn.field_agent.data;

import learn.field_agent.models.Alias;

import java.util.List;

public interface AliasRepository {

    List<Alias> findAll();

    List<Alias> findByAgentId(int agentId);

    Alias findById(int aliasId);

    Alias add(Alias alias);

    boolean update(Alias alias);

    boolean deleteById(int aliasId);
}
