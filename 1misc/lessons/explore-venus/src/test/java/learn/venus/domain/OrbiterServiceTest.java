package learn.venus.domain;

import learn.venus.data.DataAccessException;
import learn.venus.data.OrbiterFileRepository;
import learn.venus.data.OrbiterRepository;
import learn.venus.data.OrbiterRepositoryDouble;
import learn.venus.models.Orbiter;
import learn.venus.models.OrbiterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrbiterServiceTest {

    OrbiterService service;

    @BeforeEach
    void setup() {
        service = new OrbiterService(new OrbiterRepositoryDouble());
    }

    @Test
    void shouldFindByType() throws DataAccessException {
        List<Orbiter> astronauts = service.findByType(OrbiterType.ASTRONAUT);
        assertEquals(2, astronauts.size());
    }

    @Test
    void shouldNotAddNullOrbiter() throws DataAccessException {
        OrbiterResult result = service.add(null);
        assertFalse(result.isSuccess());

    }

    @Test
    void shouldNotAddAstronautWithNoRoom() throws DataAccessException {
        OrbiterResult result = service.add(
                new Orbiter(0, "Test Astro", OrbiterType.ASTRONAUT, null));
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldAddAstronaut() throws DataAccessException {
        service.add(new Orbiter(0, "Test Mod", OrbiterType.MODULE, null));
        OrbiterResult result = service.add(
                new Orbiter(0, "Test Astro", OrbiterType.ASTRONAUT, null));
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
    }

    @Test
    void shouldNotAddShuttleWithNoRoom() throws DataAccessException {
        OrbiterResult result = service.add(
                new Orbiter(0, "Test Shuttle", OrbiterType.SHUTTLE, null));
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldAddShuttle() throws DataAccessException {
        service.add(new Orbiter(0, "Test Mod with Dock", OrbiterType.MODULE_WITH_DOCK, null));
        OrbiterResult result = service.add(
                new Orbiter(0, "Test Shuttle", OrbiterType.SHUTTLE, null));
        assertTrue(result.isSuccess());
    }

    // R

    // U

    @Test
    void shouldUpdate() throws DataAccessException {
        OrbiterResult result = service.update(new Orbiter(3, "Updated Astro", OrbiterType.ASTRONAUT, null));
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateType() throws DataAccessException {
        OrbiterResult result = service.update(new Orbiter(3, "Updated Astro", OrbiterType.VENUSIAN, null));
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateEmptyName() throws DataAccessException {
        OrbiterResult result = service.update(new Orbiter(3, "    ", OrbiterType.ASTRONAUT, null));
        assertFalse(result.isSuccess());
    }

    // D

    @Test
    void shouldDelete() throws DataAccessException {
        OrbiterResult result = service.deleteById(3);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteDock() throws DataAccessException {
        OrbiterResult result = service.deleteById(1);
        assertFalse(result.isSuccess());
    }


}