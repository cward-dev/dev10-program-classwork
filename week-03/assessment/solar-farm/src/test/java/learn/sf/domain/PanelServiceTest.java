package learn.sf.domain;

import learn.sf.data.DataAccessException;
import learn.sf.data.PanelFileRepositoryDouble;
import learn.sf.model.Panel;
import learn.sf.model.PanelMaterial;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PanelServiceTest {

    PanelService service = new PanelService(new PanelFileRepositoryDouble());

    @Test
    void shouldFindAll() throws DataAccessException {
        List<Panel> panels = service.findAll();

        assertEquals(4, panels.size());
    }

    @Test
    void shouldFindBySection() throws DataAccessException {
        List<Panel> panels = service.findBySection("Jazz");

        assertEquals(2, panels.size());
        assertEquals("Jazz", panels.get(0).getSection());
    }

    @Test
    void shouldFindByMaterial() throws DataAccessException {
        List<Panel> panels = service.findByMaterial(PanelMaterial.AMORPHOUS_SILICON);

        assertEquals(2, panels.size());
        assertEquals(PanelMaterial.AMORPHOUS_SILICON, panels.get(0).getMaterial());
    }

    @Test
    void shouldFindById() throws DataAccessException {
        Panel panel = service.findById(1);

        assertNotNull(panel);
        assertEquals(1, panel.getPanelId());
        assertEquals("Bluegrass", panel.getSection());
        assertEquals(3, panel.getRow());
        assertEquals(15, panel.getColumn());
        assertEquals(1994, panel.getYearInstalled());
        assertEquals(PanelMaterial.MULTICRYSTALLINE_SILICON, panel.getMaterial());
        assertTrue(panel.isTracking());
    }

    @Test
    void shouldFindByLocation() throws DataAccessException {
        Panel panel = service.findByLocation("Bluegrass", 3, 15);

        assertNotNull(panel);
        assertEquals(1, panel.getPanelId());
        assertEquals("Bluegrass", panel.getSection());
        assertEquals(3, panel.getRow());
        assertEquals(15, panel.getColumn());
        assertEquals(1994, panel.getYearInstalled());
        assertEquals(PanelMaterial.MULTICRYSTALLINE_SILICON, panel.getMaterial());
        assertTrue(panel.isTracking());
    }

    @Test
    void shouldGetAllSections() throws DataAccessException {
        List<String> sections = service.getAllSections();

        assertEquals(3, sections.size());
    }

    @Test
    void shouldAddNewPanel() throws DataAccessException {
        Panel panel = new Panel("Bluegrass",56,43,2020,PanelMaterial.MULTICRYSTALLINE_SILICON,true);
        PanelResult expected = new PanelResult();
        expected.setPayload(panel);

        PanelResult actual = service.add(panel);

        assertTrue(actual.isSuccess());
        assertEquals(expected, actual);

        assertEquals(5, actual.getPayload().getPanelId()); // added panel id should be correct
    }

    @Test
    void shouldNotAddNull() throws DataAccessException {
        Panel panel = null;
        PanelResult expected = makeResult("Panel cannot be null.");
        PanelResult actual = service.add(panel);

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddEmptySection() throws DataAccessException {
        Panel panel = new Panel(" ",56,43,2020,PanelMaterial.MULTICRYSTALLINE_SILICON,true);
        PanelResult expected = makeResult("Section is required.");
        PanelResult actual = service.add(panel);

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddInvalidRow() throws DataAccessException {
        Panel panel = new Panel("Bluegrass",0,43,2020,PanelMaterial.MULTICRYSTALLINE_SILICON,true);
        Panel panel2 = new Panel("Bluegrass",251,43,2020,PanelMaterial.MULTICRYSTALLINE_SILICON,true);

        PanelResult actual = service.add(panel);
        PanelResult actual2 = service.add(panel2);

        PanelResult expected = makeResult("Row must be a positive number less than or equal to 250.");

        assertEquals(expected, actual);
        assertEquals(expected, actual2);
    }

    @Test
    void shouldNotAddInvalidColumn() throws DataAccessException {
        Panel panel = new Panel("Bluegrass",56,0,2020,PanelMaterial.MULTICRYSTALLINE_SILICON,true);
        Panel panel2 = new Panel("Bluegrass",56,251,2020,PanelMaterial.MULTICRYSTALLINE_SILICON,true);

        PanelResult actual = service.add(panel);
        PanelResult actual2 = service.add(panel2);

        PanelResult expected = makeResult("Column must be a positive number less than or equal to 250.");

        assertEquals(expected, actual);
        assertEquals(expected, actual2);
    }

    @Test
    void shouldNotAddWithNullMaterial() throws DataAccessException {
        Panel panel = new Panel("Bluegrass",56,43,2020,null,true);
        PanelResult actual = service.add(panel);
        PanelResult expected = makeResult("Material is required.");

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddDuplicateLocation() throws DataAccessException {
        Panel panel = new Panel("Bluegrass",3,15,1994,PanelMaterial.AMORPHOUS_SILICON,false);
        PanelResult actual = service.add(panel);
        PanelResult expected = makeResult("A panel already exists at Section: Bluegrass, Row: 3, Column: 15.");

        assertFalse(actual.isSuccess());
        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdate() throws DataAccessException {
        Panel panel = new Panel("Jazz",56,43,2020,PanelMaterial.MULTICRYSTALLINE_SILICON,true);
        panel.setPanelId(1);
        PanelResult expected = new PanelResult();
        expected.setPayload(panel);

        PanelResult actual = service.update(panel);

        assertTrue(actual.isSuccess());
        expected.setPayload(panel);
        assertEquals(expected, actual);

        assertEquals("Jazz", actual.getPayload().getSection()); // payload section should be correct
        assertEquals("Jazz", service.findById(1).getSection()); // updated data should be correct
    }

    @Test
    void shouldNotUpdateInvalidId() throws DataAccessException {
        Panel panel = new Panel("Jazz",56,43,2020,PanelMaterial.MULTICRYSTALLINE_SILICON,true);
        panel.setPanelId(100000);
        PanelResult expected = makeResult("Panel Id 100000 not found.");
        expected.setPayload(panel);

        PanelResult actual = service.update(panel);

        assertFalse(actual.isSuccess());
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateToOccupiedSpace() throws DataAccessException {
        Panel panel = new Panel("Jazz",54,22,2020,PanelMaterial.MULTICRYSTALLINE_SILICON,true);
        panel.setPanelId(1);
        PanelResult expected = makeResult("A panel already exists at Section: Jazz, Row: 54, Column: 22.");
        expected.setPayload(panel);

        PanelResult actual = service.update(panel);

        assertFalse(actual.isSuccess());
        assertEquals(expected, actual);
    }

    @Test
    void shouldDeleteById() throws DataAccessException {
        PanelResult actual = service.deleteById(1);
        PanelResult expected = new PanelResult();
        expected.setPayload(new Panel("Bluegrass",3,15,1994,PanelMaterial.MULTICRYSTALLINE_SILICON,true));
        expected.getPayload().setPanelId(1);

        assertTrue(actual.isSuccess());
        assertEquals(expected, actual);

        assertEquals("Bluegrass", actual.getPayload().getSection());
        assertNull(service.findById(1));
    }

    @Test
    void shouldNotDeleteInvalidId() throws DataAccessException {
        PanelResult actual = service.deleteById(100000);
        PanelResult expected = makeResult("Panel Id 100000 not found.");

        assertFalse(actual.isSuccess());
        assertNull(actual.getPayload());
    }

    // Helper method for expected PanelResult error messages
    private PanelResult makeResult(String message) {
        PanelResult result = new PanelResult();
        result.addErrorMessage(message);
        return result;
    }

}