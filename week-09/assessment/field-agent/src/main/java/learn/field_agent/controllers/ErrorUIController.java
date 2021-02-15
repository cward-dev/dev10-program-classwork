package learn.field_agent.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorUIController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = "/error")
    public String error() {
        return "not-found";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}