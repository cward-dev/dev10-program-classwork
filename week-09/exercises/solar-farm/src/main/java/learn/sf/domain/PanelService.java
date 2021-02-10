package learn.sf.domain;

import learn.sf.data.DataAccessException;
import learn.sf.data.PanelRepository;
import learn.sf.model.Panel;
import learn.sf.model.PanelMaterial;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
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

    public Panel findByLocation(String section, int row, int column) throws DataAccessException {
        return repository.findByLocation(section, row, column);
    }

    public List<String> getAllSections() throws DataAccessException {
        return repository.getAllSections();
    }

    public PanelResult add(Panel panel) throws DataAccessException {
        PanelResult result = new PanelResult();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Panel>> violations = validator.validate(panel);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Panel> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }

        boolean duplicate = checkForDuplicate(panel);
        if (duplicate) {
            result.addErrorMessage(String.format("A panel already exists at Section: %s, Row: %s, Column: %s.", panel.getSection(), panel.getRow(), panel.getColumn()));
            return result;
        }

        panel = repository.add(panel);
        result.setPayload(panel);

        return result;
    }

    public PanelResult update(Panel panel) throws DataAccessException {
        PanelResult result = new PanelResult();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Panel>> violations = validator.validate(panel);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Panel> violation : violations) {
                result.addErrorMessage(violation.getMessage());
            }
            return result;
        }

        boolean duplicate = checkForDuplicate(panel);
        if (duplicate) {
            result.addErrorMessage(String.format("A panel already exists at Section: %s, Row: %s, Column: %s.", panel.getSection(), panel.getRow(), panel.getColumn()));
            return result;
        }

        boolean success = repository.update(panel);
        if (!success) {
            result.addErrorMessage(String.format("Panel Id %s not found.", panel.getPanelId()));
        }
        result.setPayload(panel);

        return result;
    }

    public PanelResult deleteById(int panelId) throws DataAccessException {
        Panel panel = findById(panelId);


        PanelResult result = new PanelResult();
        boolean success = repository.deleteById(panelId);
        if (!success) {
            result.addErrorMessage(String.format("Panel Id %s not found", panelId));
        }
        result.setPayload(panel);

        return result;
    }

    public List<PanelMaterial> getMaterials() {
        return Arrays.asList(PanelMaterial.values());
    }

    private boolean checkForDuplicate(Panel panel) throws DataAccessException {
        List<Panel> panels = findAll();

        for (Panel p : panels) {
            if (Objects.equals(panel.getSection(), p.getSection())
                                    && Objects.equals(panel.getRow(), p.getRow())
                                    && Objects.equals(panel.getColumn(), p.getColumn())
                                    && !Objects.equals(panel.getPanelId(), p.getPanelId())) {
                return true;
            }
        }

        return false;
    }
}
