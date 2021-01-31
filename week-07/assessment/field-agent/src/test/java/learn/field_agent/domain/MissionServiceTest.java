package learn.field_agent.domain;

import learn.field_agent.data.AgencyRepository;
import learn.field_agent.data.MissionRepository;
import learn.field_agent.models.Agency;
import learn.field_agent.models.Mission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MissionServiceTest {

    @Autowired
    MissionService service;

    @MockBean
    MissionRepository repository;

    @MockBean
    AgencyRepository agencyRepository;

    @Test
    void shouldFindAll() {
        // pass-through test, probably not useful
        List<Mission> expected = List.of(
                new Mission(1, "Operation Piggy", "Oink oink.",
                        LocalDate.of(1970, 1, 15),
                        LocalDate.of(1970, 2, 15),
                        LocalDate.of(1970, 2, 24),
                        new BigDecimal("150000.00"),
                        1),
                new Mission(2, "Jump the Gun", "They'll never see it coming.",
                        LocalDate.of(1970, 1, 15),
                        LocalDate.of(1970, 2, 15),
                        LocalDate.of(1970, 2, 24),
                        new BigDecimal("100500.50"),
                        1),
                new Mission(3, "Taco Tuesday", "This may get messy.",
                        LocalDate.of(1970, 1, 15),
                        LocalDate.of(1970, 2, 15),
                        LocalDate.of(1970, 2, 24),
                        new BigDecimal("4506.22"),
                        2));

        when(repository.findAll()).thenReturn(expected);
        List<Mission> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindOperationPiggy() {
        // pass-through test, probably not useful
        Mission expected = makeMission();
        when(repository.findById(1)).thenReturn(expected);

        Mission actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByAgencyId() {
        Agency agency = makeAgency();
        List<Mission> expected = List.of(
                new Mission(1, "Operation Piggy", "Oink oink.",
                        LocalDate.of(1970, 1, 15),
                        LocalDate.of(1970, 2, 15),
                        LocalDate.of(1970, 2, 24),
                        new BigDecimal("150000.00"),
                        1),
                new Mission(2, "Jump the Gun", "They'll never see it coming.",
                        LocalDate.of(1970, 1, 15),
                        LocalDate.of(1970, 2, 15),
                        LocalDate.of(1970, 2, 24),
                        new BigDecimal("100500.50"),
                        1));

        when(repository.findByAgencyId(1)).thenReturn(expected);
        when(agencyRepository.findById(1)).thenReturn(agency);

        List<Mission> actual = service.findByAgencyId(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByAgencyIdNotPresent() {
        when(agencyRepository.findById(1)).thenReturn(null);

        List<Mission> actual = service.findByAgencyId(45);
        assertEquals(0, actual.size());
    }

    @Test
    void shouldAddWithNotes() {
        Mission expected = makeMission();
        Mission actual = makeMission();
        actual.setMissionId(0);

        when(repository.add(actual)).thenReturn(expected);
        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.add(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldAddWithNullNotes() {
        Mission expected = makeMission();
        expected.setNotes(null);
        Mission actual = makeMission();
        actual.setMissionId(0);
        actual.setNotes(null);

        when(repository.add(actual)).thenReturn(expected);
        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.add(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldAddAndMakeNotesNullIfBlank() {
        Mission expected = makeMission();
        expected.setNotes(null);

        Mission actual = makeMission();
        actual.setMissionId(0);
        actual.setNotes("     ");

        when(repository.add(actual)).thenReturn(expected);
        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.add(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
        assertNull(actual.getNotes());
    }

    @Test
    void shouldNotAddNull() {
        Result<Mission> result = service.add(null);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("mission cannot be null", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullOrBlankCodeName() {
        Mission actual = makeMission();
        actual.setMissionId(0);
        actual.setCodeName(null);

        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("codeName is required", result.getMessages().get(0));
        assertNull(result.getPayload());

        actual.setCodeName(" ");

        result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("codeName is required", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullStartDate() {
        Mission actual = makeMission();
        actual.setMissionId(0);
        actual.setStartDate(null);

        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("startDate is required", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullOrInvalidProjectedEndDate() {
        Mission actual = makeMission();
        actual.setMissionId(0);
        actual.setProjectedEndDate(null);

        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("projectedEndDate is required", result.getMessages().get(0));
        assertNull(result.getPayload());

        actual.setProjectedEndDate(actual.getStartDate().minusDays(1));

        result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("projectedEndDate cannot be before startDate", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullOrInvalidActualEndDate() {
        Mission actual = makeMission();
        actual.setMissionId(0);
        actual.setActualEndDate(null);

        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("actualEndDate is required", result.getMessages().get(0));
        assertNull(result.getPayload());

        actual.setActualEndDate(actual.getStartDate().minusDays(1));

        result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("actualEndDate cannot be before startDate", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullOrNegativeOperationalCost() {
        Mission actual = makeMission();
        actual.setMissionId(0);
        actual.setOperationalCost(null);

        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("operationalCost is required", result.getMessages().get(0));
        assertNull(result.getPayload());

        actual.setOperationalCost(new BigDecimal("-45.00"));

        result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("operationalCost cannot be a negative amount", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddInvalidAgencyId() {
        Mission actual = makeMission();
        actual.setMissionId(0);
        actual.setAgencyId(45);

        when(agencyRepository.findById(45)).thenReturn(null);
        Result<Mission> result = service.add(actual);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("agencyId: 45, not found", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddDuplicate() {
        Mission actual = makeMission();
        actual.setMissionId(0);

        when(repository.findAll()).thenReturn(List.of(makeMission()));
        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("codeName: 'Operation Piggy', agencyId: 1, already exists", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddWithId() {
        Mission actual = makeMission();
        actual.setMissionId(45);

        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.add(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("missionId cannot be set for `add` operation", result.getMessages().get(0));
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdate() {
        Mission actual = makeMission();
        actual.setCodeName("Operation Piggy: Electric Boogaloo.");

        when(repository.update(actual)).thenReturn(true);
        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.update(actual);

        assertEquals(ResultType.SUCCESS, result.getType());
    }


    @Test
    void shouldNotUpdateMissingOrInvalidId() {
        Mission actual = new Mission(0, "Operation Test", "Test.",
                LocalDate.of(1970, 1, 15),
                LocalDate.of(1970, 2, 15),
                LocalDate.of(1970, 2, 24),
                new BigDecimal("3.50"),
                1);

        when(repository.findAll()).thenReturn(List.of(makeMission()));
        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("missionId must be set for `update` operation", result.getMessages().get(0));

        actual.setMissionId(45);
        result = service.update(actual);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("missionId: 45, not found", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNullOrBlankCodeName() {
        Mission actual = makeMission();
        actual.setCodeName(null);

        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("codeName is required", result.getMessages().get(0));

        actual.setCodeName("   ");
        result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("codeName is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNullStartDate() {
        Mission actual = makeMission();
        actual.setStartDate(null);

        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("startDate is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNullOrInvalidProjectedEndDate() {
        Mission actual = makeMission();
        actual.setProjectedEndDate(null);

        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("projectedEndDate is required", result.getMessages().get(0));

        actual.setProjectedEndDate(actual.getStartDate().minusDays(1));

        result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("projectedEndDate cannot be before startDate", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNullOrInvalidActualEndDate() {
        Mission actual = makeMission();
        actual.setActualEndDate(null);

        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("actualEndDate is required", result.getMessages().get(0));

        actual.setActualEndDate(actual.getStartDate().minusDays(1));

        result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("actualEndDate cannot be before startDate", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNullOrNegativeOperationalCost() {
        Mission actual = makeMission();
        actual.setOperationalCost(null);

        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("operationalCost is required", result.getMessages().get(0));

        actual.setOperationalCost(new BigDecimal("-10.00"));

        result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("operationalCost cannot be a negative amount", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateInvalidAgencyId() {
        Mission actual = makeMission();
        actual.setAgencyId(45);

        when(agencyRepository.findById(45)).thenReturn(null);
        Result<Mission> result = service.update(actual);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("agencyId: 45, not found", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateDuplicate() {
        Mission mission2 = new Mission(2, "Test", "Test",
                LocalDate.of(1970,6,1),
                LocalDate.of(1970,6,10),
                LocalDate.of(1970,6,10),
                new BigDecimal("3.50"),
                1);

        Mission actual = makeMission();
        actual.setCodeName("Test");

        when(repository.findAll()).thenReturn(List.of(makeMission(), mission2));
        when(agencyRepository.findById(1)).thenReturn(makeAgency());
        Result<Mission> result = service.update(actual);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("codeName: 'Test', agencyId: 1, already exists", result.getMessages().get(0));
        assertNull(result.getPayload());
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

    private Mission makeMission() {
        //(1, 'Operation Piggy', 'Oink oink.', '1970-01-15', '1970-02-15', '1970-02-24', 150000.00, 1)
        Mission mission = new Mission();
        mission.setMissionId(1);
        mission.setCodeName("Operation Piggy");
        mission.setNotes("Oink oink.");
        mission.setStartDate(LocalDate.of(1970, 1, 15));
        mission.setProjectedEndDate(LocalDate.of(1970, 2, 15));
        mission.setActualEndDate(LocalDate.of(1970, 2, 24));
        mission.setOperationalCost(new BigDecimal("150000.00"));
        mission.setAgencyId(1);
        return mission;
    }

    private Agency makeAgency() {
        //(1, 'ACME', 'Agency to Classify & Monitor Evildoers')
        Agency agency = new Agency();
        agency.setAgencyId(1);
        agency.setShortName("ACME");
        agency.setLongName("Agency to Classify & Monitor Evildoers");
        return agency;
    }
}