package learn.field_agent.domain;

import learn.field_agent.data.AgentRepository;
import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AliasServiceTest {

    @Autowired
    AliasService service;

    @MockBean
    AliasRepository repository;

    @MockBean
    AgentRepository agentRepository;

    @Test
    void shouldFindAll() {
        // pass-through test, probably not useful
        List<Alias> expected = List.of(
                new Alias(1, "Red Noodle", null, 1),
                new Alias(2, "Watcher", null, 1),
                new Alias(3, "007", null, 2),
                new Alias(4, "007", "Rebooted", 3));
        when(repository.findAll()).thenReturn(expected);
        List<Alias> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindRedNoodle() {
        // pass-through test, probably not useful
        Alias expected = makeAlias();
        when(repository.findById(1)).thenReturn(expected);

        Alias actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByAgentId() {
        List<Agent> agents = List.of(makeAgent());
        List<Alias> expected = List.of(
                new Alias(1, "Red Noodle", null, 1),
                new Alias(2, "Watcher", null, 1));

        when(repository.findByAgentId(1)).thenReturn(expected);
        when(agentRepository.findAll()).thenReturn(agents);

        List<Alias> actual = service.findByAgentId(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByAgentIdNotPresent() {
        List<Agent> agents = List.of(
                new Agent(1, "Hazel", "C", "Sauven",
                        LocalDate.of(1954, 9, 16), 76));

        when(agentRepository.findAll()).thenReturn(agents);

        List<Alias> actual = service.findByAgentId(45);
        assertEquals(0, actual.size());
    }

    @Test
    void shouldAddNullPersona() {
        Alias expected = makeAlias();
        Alias actual = makeAlias();
        actual.setAliasId(0);

        when(repository.add(actual)).thenReturn(expected);
        when(agentRepository.findAll()).thenReturn(List.of(makeAgent()));

        Result<Alias> result = service.add(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldAddWithPersona() {
        Alias expected = makeAlias();
        expected.setAliasId(1);
        expected.setPersona("Test Persona");
        Alias actual = makeAlias();
        actual.setAliasId(0);
        actual.setPersona("Test Persona");

        when(repository.add(actual)).thenReturn(expected);
        when(agentRepository.findAll()).thenReturn(List.of(makeAgent()));
        Result<Alias> result = service.add(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldAddAndMakePersonaNullIfBlank() {
        Alias actual = makeAlias();
        actual.setAliasId(0);
        actual.setPersona("     ");

        when(repository.add(actual)).thenReturn(makeAlias());
        when(agentRepository.findAll()).thenReturn(List.of(makeAgent()));
        Result<Alias> result = service.add(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertNull(actual.getPersona());
    }

    @Test
    void shouldNotAddNull() {
        Result<Alias> result = service.add(null);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("alias cannot be null", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullOrBlankName() {
        Alias actual = makeAlias();
        actual.setAliasId(0);
        actual.setName(null);

        when(agentRepository.findAll()).thenReturn(List.of(makeAgent()));
        Result<Alias> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("name is required", result.getMessages().get(0));
        assertNull(result.getPayload());

        actual.setName(" ");

        result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("name is required", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddDuplicateNameWithNullPersona() {
        Alias actual = makeAlias();
        actual.setAliasId(0);

        when(agentRepository.findAll()).thenReturn(List.of(makeAgent()));
        when(repository.findAll()).thenReturn(List.of(makeAlias()));
        Result<Alias> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("name: 'Bill', already exists, cannot set without persona", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddDuplicateNameAndPersona() {
        Alias alias = makeAlias();
        alias.setPersona("Test Persona");

        Alias actual = makeAlias();
        actual.setAliasId(0);
        actual.setPersona("Test Persona");

        when(agentRepository.findAll()).thenReturn(List.of(makeAgent()));
        when(repository.findAll()).thenReturn(List.of(alias));
        Result<Alias> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("name: 'Bill', persona: 'Test Persona', already exists", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddWithId() {
        Alias actual = makeAlias();
        actual.setAliasId(45);

        when(agentRepository.findAll()).thenReturn(List.of(makeAgent()));
        Result<Alias> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("aliasId cannot be set for `add` operation", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdate() {
        Alias actual = makeAlias();
        actual.setName("Hopper");

        when(repository.update(actual)).thenReturn(true);
        when(repository.findAll()).thenReturn(List.of(makeAlias()));
        when(agentRepository.findAll()).thenReturn(List.of(makeAgent()));
        Result<Alias> result = service.update(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldUpdateToSameNameAndPersonaCombinationAsExisting() {
        Alias actual = makeAlias();

        when(repository.update(actual)).thenReturn(true);
        when(repository.findAll()).thenReturn(List.of(makeAlias()));
        when(agentRepository.findAll()).thenReturn(List.of(makeAgent()));
        Result<Alias> result = service.update(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotUpdateNullOrBlankName() {
        Alias actual = makeAlias();
        actual.setName(null);

        when(repository.findAll()).thenReturn(List.of(makeAlias()));
        when(agentRepository.findAll()).thenReturn(List.of(makeAgent()));
        Result<Alias> result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("name is required", result.getMessages().get(0));

        actual.setName(" ");
        result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("name is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateDuplicateNameNullPersona() {
        Alias alias2 = new Alias(2, "Test", null, 1);

        Alias actual = makeAlias();
        actual.setName("Test");
        actual.setPersona(null);

        when(repository.findAll()).thenReturn(List.of(makeAlias(), alias2));
        when(agentRepository.findAll()).thenReturn(List.of(makeAgent()));
        Result<Alias> result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("name: 'Test', already exists, cannot set without persona", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateDuplicateNameAndPersona() {
        Alias alias2 = new Alias(2, "Test", "V2", 1);

        Alias actual = makeAlias();
        actual.setName("Test");
        actual.setPersona("V2");

        when(repository.findAll()).thenReturn(List.of(makeAlias(), alias2));
        when(agentRepository.findAll()).thenReturn(List.of(makeAgent()));
        Result<Alias> result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("name: 'Test', persona: 'V2', already exists", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateInvalidAgentId() {
        Alias actual = makeAlias();
        actual.setAgentId(45);

        when(repository.findAll()).thenReturn(List.of(makeAlias()));
        when(agentRepository.findAll()).thenReturn(List.of(makeAgent()));
        Result<Alias> result = service.update(actual);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("agentId: 45, not found", result.getMessages().get(0));
    }


    private Alias makeAlias() {
        //('Bill',null,1)
        Alias alias = new Alias();
        alias.setAliasId(1);
        alias.setName("Bill");
        alias.setPersona(null);
        alias.setAgentId(1);
        return alias;
    }

    private Agent makeAgent() {
        //('Hazel','C','Sauven','1954-09-16',76),
        Agent agent = new Agent();
        agent.setAgentId(1);
        agent.setFirstName("Hazel");
        agent.setMiddleName("C");
        agent.setLastName("Sauven");
        agent.setDob(LocalDate.of(1954, 9, 16));
        agent.setHeightInInches(76);
        return agent;
    }

    @Test
    void shouldDeleteById() {
        // pass-through test, probably not useful
        when(repository.deleteById(1)).thenReturn(true);
        assertTrue(service.deleteById(1));
    }

    @Test
    void shouldNotDeleteByIdNotFound() {
        // pass-through test, probably not useful
        when(repository.deleteById(45)).thenReturn(false);
        assertFalse(service.deleteById(45));
    }
}