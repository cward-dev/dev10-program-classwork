package learn.field_agent.domain;

import learn.field_agent.data.AgencyAgentRepository;
import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.AgencyAgent;
import learn.field_agent.models.Agent;
import learn.field_agent.models.SecurityClearance;
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
class SecurityClearanceServiceTest {

    @Autowired
    SecurityClearanceService service;

    @MockBean
    SecurityClearanceRepository repository;

    @MockBean
    AgencyAgentRepository agencyAgentRepository;

    @Test
    void shouldFindAll() {
        // pass-through test, probably not useful
        List<SecurityClearance> expected = List.of(
                new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret"),
                new SecurityClearance(3, "Ultra Top Secret"));
        when(repository.findAll()).thenReturn(expected);

        List<SecurityClearance> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindSecret() {
        // pass-through test, probably not useful
        SecurityClearance expected = makeSecurityClearance();
        when(repository.findById(1)).thenReturn(expected);

        SecurityClearance actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd() {
        SecurityClearance expected = makeSecurityClearance();
        SecurityClearance actual = makeSecurityClearance();
        actual.setSecurityClearanceId(0);

        when(repository.add(actual)).thenReturn(expected);
        Result<SecurityClearance> result = service.add(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddNull() {
        Result<SecurityClearance> result = service.add(null);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("securityClearance cannot be null", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullOrBlankName() {
        SecurityClearance actual = makeSecurityClearance();
        actual.setName(null);

        Result<SecurityClearance> result = service.add(actual);

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
    void shouldNotAddDuplicateName() {
        when(repository.findAll()).thenReturn(List.of(makeSecurityClearance()));

        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(0);
        Result<SecurityClearance> result = service.add(securityClearance);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("name: 'Test Security Clearance', already exists", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddWithId() {
        SecurityClearance actual = makeSecurityClearance();

        Result<SecurityClearance> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("securityClearanceId cannot be set for `add` operation", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdate() {
        SecurityClearance securityClearance = makeSecurityClearance();

        when(repository.update(securityClearance)).thenReturn(true);

        Result<SecurityClearance> result = service.update(securityClearance);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotUpdateNull() {
        Result<SecurityClearance> result = service.update(null);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("securityClearance cannot be null", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNullOrBlankName() {
        SecurityClearance actual = makeSecurityClearance();
        actual.setName(null);

        Result<SecurityClearance> result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("name is required", result.getMessages().get(0));
        assertNull(result.getPayload());

        actual.setName(" ");

        result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("name is required", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateInvalidId() {
        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(-45);

        Result<SecurityClearance> result = service.update(securityClearance);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("securityClearanceId must be set be set for `update` operation",
                result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateIdNotFound() {
        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(45);

        when(repository.update(securityClearance)).thenReturn(false);

        Result<SecurityClearance> result = service.update(securityClearance);
        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("securityClearanceId: 45, not found",
                result.getMessages().get(0));
    }

    @Test
    void shouldDeleteById() {
        when(repository.deleteById(1)).thenReturn(true);
        when(agencyAgentRepository.findBySecurityClearanceId(1)).thenReturn(new ArrayList<>());

        Result<SecurityClearance> result = service.deleteById(1);
        assertTrue(result.isSuccess());
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDeleteByIdNotFound() {
        when(repository.deleteById(45)).thenReturn(false);
        when(agencyAgentRepository.findBySecurityClearanceId(1)).thenReturn(new ArrayList<>());

        Result<SecurityClearance> result = service.deleteById(45);
        assertFalse(result.isSuccess());
        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("securityClearanceId: 45, not found",
                result.getMessages().get(0));
    }

    @Test
    void shouldNotDeleteByIdWithDependentAgencyAgent() {
        when(agencyAgentRepository.findBySecurityClearanceId(1)).thenReturn(List.of(makeAgencyAgent()));

        Result<SecurityClearance> result = service.deleteById(1);
        assertFalse(result.isSuccess());
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("cannot delete a securityClearance with dependent agencyAgent",
                result.getMessages().get(0));
    }

    private SecurityClearance makeSecurityClearance() {
        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setSecurityClearanceId(1);
        securityClearance.setName("Test Security Clearance");
        return securityClearance;
    }

    Agent makeAgent() {
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

    private AgencyAgent makeAgencyAgent() {
        AgencyAgent agencyAgent = new AgencyAgent();
        agencyAgent.setAgencyId(1);
        agencyAgent.setIdentifier("Test");
        agencyAgent.setActivationDate(LocalDate.of(1970, 1, 1));
        agencyAgent.setActive(true);

        agencyAgent.setAgent(makeAgent());
        agencyAgent.setSecurityClearance(makeSecurityClearance());
        return agencyAgent;
    }
}