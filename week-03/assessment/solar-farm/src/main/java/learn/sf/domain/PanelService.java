package learn.sf.domain;

import learn.sf.data.DataAccessException;
import learn.sf.data.PanelRepository;
import learn.sf.model.Panel;
import learn.sf.model.PanelMaterial;

import java.util.List;
import java.util.Objects;

public class PanelService {

    PanelRepository repository;

    public PanelService(PanelRepository repository) {
        this.repository = repository;
    }

    public List<Panel> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public List<Panel> findBySection(String section) throws DataAccessException {
        return repository.findBySection(section);
    }

    public List<Panel> findByMaterial(PanelMaterial material) throws DataAccessException {
        return repository.findByMaterial(material);
    }

    public Panel findById(int panelId) throws DataAccessException {
        return repository.findById(panelId);
    }

    public PanelResult add(Panel panel) throws DataAccessException {
        PanelResult result = validate(panel);
        if (result.isSuccess()) {
            return result;
        }

        boolean duplicate = checkForDuplicate(panel);
        if (duplicate) {
            result.addErrorMessage(String.format("A panel already exists at Section: %s, Row: %s, Column: %s.", result.getPayload().getSection(), result.getPayload().getRow(), result.getPayload().getColumn()));
        }

        panel = repository.add(panel);
        result.setPayload(panel);

        return result;
    }

    public PanelResult update(Panel panel) throws DataAccessException {
        PanelResult result = validate(panel);
        if (result.isSuccess()) {
            return result;
        }

        boolean duplicate = checkForDuplicate(panel);
        if (duplicate) {
            result.addErrorMessage(String.format("A panel already exists at Section: %s, Row: %s, Column: %s.", result.getPayload().getSection(), result.getPayload().getRow(), result.getPayload().getColumn()));
        }

        boolean success = repository.update(panel);
        if (!success) {
            result.addErrorMessage(String.format("Panel Id %s not found.", panel.getPanelId()));
        }

        return result;
    }

    public PanelResult deleteById(int panelId) throws DataAccessException {
        Panel panel = findById(panelId);
        PanelResult result = validate(panel);
        if (result.isSuccess()) {
            return result;
        }

        boolean success = repository.deleteById(panelId);
        if (!success) {
            result.addErrorMessage(String.format("Panel Id %s not found", panelId));
        }

        return result;
    }

    private PanelResult validate(Panel panel) {
        PanelResult result = new PanelResult();

        if (panel == null) {
            result.addErrorMessage("Panel cannot be null.");
        }

        if (panel.getSection() == null || panel.getSection().trim().length() == 0) {
            result.addErrorMessage("Section is required.");
        }

        if (panel.getRow() < 1 || panel.getRow() > 250) {
            result.addErrorMessage("Row must be a positive number less than or equal to 250.");
        }

        if (panel.getColumn() < 1 || panel.getColumn() > 250) {
            result.addErrorMessage("Column must be a positive number less than or equal to 250.");
        }

        if (panel.getMaterial() == null) {
            result.addErrorMessage("Material is required.");
        }

        return result;
    }

    private boolean checkForDuplicate(Panel panel) throws DataAccessException {
        List<Panel> panels = findAll();

        for (Panel p : panels) {
            if (Objects.equals(panel.getSection(), p.getSection())
                                    && Objects.equals(panel.getRow(), p.getRow())
                                    && Objects.equals(panel.getColumn(), p.getColumn())) {
                return true;
            }
        }

        return false;
    }


}
