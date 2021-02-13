package learn.field_agent.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainMenuController {

    @GetMapping("/")
    public String displayMainMenu() {
        return "index";
    }
}
