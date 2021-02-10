package learn.sf.controllers;

import learn.sf.data.DataAccessException;
import learn.sf.domain.PanelResult;
import learn.sf.domain.PanelService;
import learn.sf.model.Panel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PanelUIController {

    private final PanelService service;

    public PanelUIController(PanelService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String displayAll(Model model) throws DataAccessException {
        model.addAttribute("panels", service.findAll());
        return "index";
    }

    @GetMapping("/add")
    public String displayAdd(@ModelAttribute("panel") Panel panel, Model model) {
        model.addAttribute("materials", service.getMaterials());
        return "form";
    }

    @PostMapping("/add")
    public String handleAdd(
            @ModelAttribute("panel") @Valid Panel panel,
            BindingResult result, Model model) throws DataAccessException {

        if (panel.getMaterial() == null) {
            result.addError(new FieldError("panel", "material", "select a material"));
        }

        if (result.hasErrors()) {
            model.addAttribute("materials", service.getMaterials());
            return "form";
        }

        PanelResult serviceResult = service.add(panel);

        return "redirect:/";
    }

    @GetMapping("/edit/{panelId}")
    public String displayEdit(@PathVariable int panelId, Model model) throws DataAccessException {
        Panel panel = service.findById(panelId);
        model.addAttribute("panel", panel);
        model.addAttribute("materials", service.getMaterials());
        return "form";
    }

    @PostMapping("/edit/*")
    public String handleEdit(
            @ModelAttribute("panel") @Valid Panel panel,
            BindingResult result, Model model) throws DataAccessException {

        if (panel.getMaterial() == null) {
            result.addError(new FieldError("panel", "material", "select a material"));
        }

        if (result.hasErrors()) {
            model.addAttribute("materials", service.getMaterials());
            return "form";
        }

        PanelResult serviceResult = service.update(panel);

        return "redirect:/";
    }

    @GetMapping("/delete/{panelId}")
    public String displayDelete(@PathVariable int panelId, Model model) throws DataAccessException {
        Panel panel = service.findById(panelId);
        if (panel == null) {
            return "not-found";
        }

        model.addAttribute("panel", panel);
        return "delete";
    }

    @PostMapping("/delete/{panelId}")
    public String handleDelete(@PathVariable int panelId, Model model) throws DataAccessException {
        service.deleteById(panelId);
        return "redirect:/";
    }
}