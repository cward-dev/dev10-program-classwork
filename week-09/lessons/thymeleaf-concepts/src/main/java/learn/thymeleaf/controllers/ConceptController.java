package learn.thymeleaf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class ConceptController {

    private String message;

    @GetMapping("/")
    public String getHomeView(Model model) {
        model.addAttribute("now", LocalDateTime.now());
        return "home";
    }

    @GetMapping("/contact")
    public String getContactView() {
        return "contact";
    }

    @PostMapping("/contact")
    public String getContactView(String email, String message) {
        System.out.printf("Email: %s%nMessage: %s%n", email, message);
        this.message = message;
        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThankYouView(Model model) {
        model.addAttribute("message", message);
        return "thank-you";
    }
}
