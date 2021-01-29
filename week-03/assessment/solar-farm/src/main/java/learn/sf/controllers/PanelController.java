package learn.sf.controllers;

import learn.sf.data.DataAccessException;
import learn.sf.domain.PanelResult;
import learn.sf.domain.PanelService;
import learn.sf.model.Panel;
import learn.sf.model.PanelMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/panels")
public class PanelController {

    @Autowired
    private PanelService service;

    @GetMapping
    public List<Panel> findAll() throws DataAccessException {
        return service.findAll();
    }

    @GetMapping("/section/{section}")
    public List<Panel> findBySection(@PathVariable String section) throws DataAccessException {
        return service.findBySection(section);
    }

    @GetMapping("/material/{material}")
    public List<Panel> findByMaterial(@PathVariable String material) throws DataAccessException {
        return service.findByMaterial(PanelMaterial.valueOf(material));
    }

    @GetMapping("/{panelId}")
    public Panel findById(@PathVariable int panelId) throws DataAccessException {
        return service.findById(panelId);
    }

    @GetMapping("/{section}/{row}/{column}")
    public Panel findByLocation(@PathVariable String section, @PathVariable int row, @PathVariable int column) throws DataAccessException {
        return service.findByLocation(section, row, column);
    }

    @GetMapping("/sections")
    public List<String> getAllSections() throws DataAccessException {
        return service.getAllSections();
    }

    @PostMapping
    public ResponseEntity<Panel> add(@RequestBody Panel panel) throws DataAccessException {
        PanelResult result = service.add(panel);

        if (!result.isSuccess()) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
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
