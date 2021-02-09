package learn.thymeleaf.controllers;

import learn.thymeleaf.domain.ModelService;
import learn.thymeleaf.models.TShirt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TShirtController {

    private final ModelService service;

    public TShirtController(ModelService service) {
        this.service = service;
    }

    @GetMapping("/tshirts")
    public String getTShirtHome(Model model) {
        model.addAttribute("tshirts", service.getAllTShirts());
        return "tshirts/shirts";
    }

    @GetMapping("/tshirt/add")
    public String getAddView(@ModelAttribute("tshirt") TShirt shirt, Model model) {
        model.addAttribute("colors", service.getAllColors());
        model.addAttribute("sizes", service.getAllSizes());
        return "tshirts/add";
    }

    @PostMapping("/tshirt/add")
    public String handleAdd(TShirt shirt) {

        System.out.println(shirt);

        shirt.setColor(service.getColorById(shirt.getColor().getColorId()));
        shirt.setSize(service.getSizeById(shirt.getSize().getSizeId()));

        service.save(shirt);

        return "redirect:/tshirts";
    }

    @GetMapping("/tshirt/edit/{id}")
    public String getEditView(@PathVariable int id, Model model) {
        TShirt shirt = service.getTShirtById(id);
        if (shirt == null) {
            return "not-found";
        }
        model.addAttribute("tshirt", shirt);
        model.addAttribute("colors", service.getAllColors());
        model.addAttribute("sizes", service.getAllSizes());
        return "tshirts/edit";
    }

    @PostMapping("/tshirt/edit/*")
    public String handleEdit(TShirt shirt) {

        System.out.println(shirt);

        shirt.setColor(service.getColorById(shirt.getColor().getColorId()));
        shirt.setSize(service.getSizeById(shirt.getSize().getSizeId()));

        service.save(shirt);

        return "redirect:/tshirts";
    }

    @GetMapping("/tshirt/delete/{id}")
    public String getDeleteView(@PathVariable int id, Model model) {
        TShirt shirt = service.getTShirtById(id);
        if (shirt == null) {
            return "not-found";
        }
        model.addAttribute("tshirt", shirt);
        return "tshirts/delete";
    }

    @PostMapping("/tshirt/delete/{id}")
    public String handleDelete(@PathVariable int id) {
        TShirt shirt = service.getTShirtById(id);
        if (shirt == null) {
            return "not-found";
        }
        service.deleteTShirtById(id);
        return "redirect:/tshirts";
    }
}
