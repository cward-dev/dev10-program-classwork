package learn.field_agent.data;

import learn.field_agent.models.Mission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MissionJdbcTemplateRepositoryTest {

    final static int NEXT_MISSION_ID = 4;

    @Autowired
    MissionJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() { knownGoodState.set(); }

    /*
    Testing Strategy:
    * Id 1 ("Operation Piggy") will remain unaltered
    * Id 2 ("Jump the Gun") will update from "Jump the Gun" to "Jump the Fun"
    * Id 3 ("Taco Tuesday") will delete
     */

    @Test
    void shouldFindAll() {
        List<Mission> missions = repository.findAll();

        assertNotNull(missions);
        assertTrue(missions.size() > 0);
    }

    @Test
    void shouldFindByAgencyId() {
        List<Mission> missions = repository.findByAgencyId(1);

        assertNotNull(missions);
        assertTrue(missions.size() > 0);

        List<Mission> missionsInvalidAgencyId = repository.findByAgencyId(45);
        assertFalse(missionsInvalidAgencyId.size() > 0);
    }

    @Test
    void shouldFindById() {
        Mission operationPiggy = repository.findById(1);
        assertEquals("Operation Piggy", operationPiggy.getCodeName());


        Mission invalidOperation = repository.findById(45);
        assertNull(invalidOperation);
    }

    @Test
    void shouldAdd() {
        Mission mission = makeMission();
        Mission actual = repository.add(mission);
        assertNotNull(actual);
        assertEquals(NEXT_MISSION_ID, actual.getMissionId());
    }

    @Test
    void shouldUpdate() {
        Mission mission = new Mission(2,
                "Jump the Gun", "They'll never see it coming.",
                LocalDate.of(1970, 1, 15),
                LocalDate.of(1970, 2, 15),
                LocalDate.of(1970, 2, 24),
                new BigDecimal("100500.50"),
                1);
        mission.setCodeName("Jump the Fun");
        assertTrue(repository.update(mission));
        mission.setMissionId(45);
        assertFalse(repository.update(mission));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(45));
    }

    Mission makeMission() {
        Mission mission = new Mission();
        mission.setCodeName("Tester");
        mission.setNotes("Testing text.");
        mission.setStartDate(LocalDate.of(1970, 1, 15));
        mission.setProjectedEndDate(LocalDate.of(1970, 1, 16));
        mission.setActualEndDate(LocalDate.of(1970, 1, 16));
        mission.setOperationalCost(new BigDecimal("3.50"));
        mission.setAgencyId(1);
        return mission;
    }

}