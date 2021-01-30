package learn.field_agent.domain;

import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Alias;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AliasService {

    private final AliasRepository repository;

    public AliasService(AliasRepository repository) {
        this.repository = repository;
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
}
