package learn.thymeleaf.controllers;

import learn.thymeleaf.domain.ModelService;
import learn.thymeleaf.models.Listing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ListingController {

    private final ModelService service;

    public ListingController(ModelService service) {
        this.service = service;
    }

    @GetMapping("/listings")
    public String getListingsHome(Model model) {
        model.addAttribute("listings", service.getAllListings());
        return "listings/index";
    }

    @GetMapping("/listing/edit/{id}")
    public String getEditView(@PathVariable int id, Model model) {
        Listing listing = service.getListingById(id);
        if (listing == null) {
            return "not-found";
        }
        model.addAttribute("listing", listing);
        model.addAttribute("colors", service.getAllColors());
        model.addAttribute("sizes", service.getAllSizes());
        return "listings/edit";
    }

    @PostMapping("/listing/edit/*")
    public String handleEdit(Listing listing, WebRequest request) {

        System.out.println(listing);
        
        String[] colorIds = request.getParameterValues("colorIds");
        String[] sizeIds = request.getParameterValues("sizeIds");

        if (colorIds != null) {
            for (String colorId : colorIds) {
                listing.addColor(service.getColorById(Integer.parseInt(colorId)));
            }
        }

        if (sizeIds != null) {
            for (String sizeId : sizeIds) {
                listing.addSize(service.getSizeById(Integer.parseInt(sizeId)));
            }
        }

        service.save(listing);

        return "redirect:/listings";
    }
}
