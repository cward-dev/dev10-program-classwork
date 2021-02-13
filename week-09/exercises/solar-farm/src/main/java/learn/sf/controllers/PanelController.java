package learn.sf.controllers;

import learn.sf.data.DataAccessException;
import learn.sf.domain.PanelResult;
import learn.sf.domain.PanelService;
import learn.sf.model.Panel;
import learn.sf.model.PanelMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://initial-domain.com"})
@RequestMapping("/panels")
public class PanelController {

    @Autowired
    private PanelService service;

    @GetMapping
    public List<Panel> findAll() throws DataAccessException {
        return service.findAll();
    }

    @GetMapping("/section/{section}")
    public ResponseEntity<List<Panel>> findBySection(@PathVariable String section) throws DataAccessException {
        List<Panel> panels = service.findBySection(section);

        if (panels == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (panels.size() == 0) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(panels, HttpStatus.OK);
    }

    @GetMapping("/material/{material}")
    public ResponseEntity<List<Panel>> findByMaterial(@PathVariable String material) throws DataAccessException {
        List<Panel> panels = service.findByMaterial(PanelMaterial.valueOf(material));

        if (panels == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (panels.size() == 0) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(panels, HttpStatus.OK);
    }

    @GetMapping("/{panelId}")
    public ResponseEntity<Panel> findById(@PathVariable int panelId) throws DataAccessException {
        Panel panel = service.findById(panelId);
        if (panel == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(panel, HttpStatus.OK);
    }

    @GetMapping("/{section}/{row}/{column}")
    public ResponseEntity<Panel> findByLocation(
            @PathVariable String section,
            @PathVariable int row, @PathVariable int column)
            throws DataAccessException {
        Panel panel = service.findByLocation(section, row, column);

        if (panel == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(panel, HttpStatus.OK);
    }

    @GetMapping("/sections")
    public ResponseEntity<List<String>> getAllSections() throws DataAccessException {
        List<String> sections = service.getAllSections();

        if (sections == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (sections.size() == 0) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid Panel panel, BindingResult result) throws DataAccessException {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        PanelResult resultPanel = service.add(panel);

        if (!resultPanel.isSuccess()) return new ResponseEntity<>(resultPanel.getMessages(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(resultPanel.getPayload(), HttpStatus.CREATED);
    }

    @PutMapping("/{panelId}")
    public ResponseEntity<Void> update(@PathVariable int panelId, @RequestBody Panel panel) throws DataAccessException {
        if (panelId != panel.getPanelId()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        PanelResult result = service.update(panel);
        if (!result.isSuccess()) {
            if (findAll().stream().noneMatch(p -> p.getPanelId() == panelId))
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{panelId}")
    public ResponseEntity<Void> delete(@PathVariable int panelId) throws DataAccessException {
        PanelResult result = service.deleteById(panelId);

        if (!result.isSuccess()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
