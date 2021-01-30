package learn.field_agent.data;

import learn.field_agent.models.Alias;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AliasJdbcTemplateRepositoryTest {

    final static int NEXT_ALIAS_ID = 4;

    @Autowired
    AliasJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() { knownGoodState.set(); }

    /*
    Testing Strategy:
    * Id 1 ("Red Noodle") will remain unaltered
    * Id 2 ("Watcher") will update from "Watcher" to "Tester"
    * Id 3 ("007") will delete
     */

    @Test
    void shouldFindAll() {
        List<Alias> aliases = repository.findAll();

        assertNotNull(aliases);
        assertTrue(aliases.size() > 0);
    }

    @Test
    void shouldFindByAgentId() {
        List<Alias> aliases = repository.findByAgentId(1);

        assertNotNull(aliases);
        assertEquals(2, aliases.size());
    }

    @Test
    void shouldFindById() {
        Alias redNoodle = repository.findById(1);
        assertEquals("Red Noodle", redNoodle.getName());


        Alias invalidAlias = repository.findById(45);
        assertNull(invalidAlias);
    }

    @Test
    void shouldAdd() {
        Alias alias = makeSecurityClearance();
        Alias actual = repository.add(alias);
        assertNotNull(actual);
        assertEquals(NEXT_ALIAS_ID, actual.getAliasId());
    }

    @Test
    void shouldUpdate() {
        Alias alias = makeSecurityClearance();
        alias.setAliasId(2);
        assertTrue(repository.update(alias));
        alias.setAliasId(16);
        assertFalse(repository.update(alias));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(45));
    }

    Alias makeSecurityClearance() {
        Alias alias = new Alias();
        alias.setName("Tester");
        alias.setPersona(null);
        alias.setAgentId(1);
        return alias;
    }
}