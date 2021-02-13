package learn.field_agent.controllers;

import learn.field_agent.domain.AgentService;
import learn.field_agent.domain.Result;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;
import java.util.stream.Collectors;

@Controller
public class AgentUIController {

    private final AgentService service;

    public AgentUIController(AgentService service) {
        this.service = service;
    }

    @GetMapping("/agent")
    public String displayAll(Model model) {
        model.addAttribute("agents", service.findAll());
        return "agent-display";
    }

    @GetMapping("/agent/add")
    public String displayAdd(@ModelAttribute("agent") Agent agent, Model model) {
        return "agent-form";
    }

    @PostMapping("/agent/add")
    public String handleAdd(@ModelAttribute("agent") @Valid Agent agent, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "agent-form";
        }

        Result<Agent> serviceResult = service.add(agent);

        if (!serviceResult.isSuccess()) {
            for (String error : serviceResult.getMessages()) {
                result.addError(new ObjectError("agent", error));
            }
            return "agent-form";
        }

        return "redirect:/agent";
    }

    @GetMapping("/agent/add/random")
    public String displayAddRandom(Model model) {
        Agent agent = service.makeRandomAgent();
        service.add(agent); // Since duplicate names don't matter and random fields are safe, nothing to do

        return "redirect:/agent";
    }

    @GetMapping("/agent/edit/{agentId}")
    public String displayEdit(@PathVariable int agentId, Model model) {
        Agent agent = service.findById(agentId);
        if (agent == null) {
            return "not-found";
        }

        model.addAttribute("agent", agent);
        return "agent-form";
    }

    @PostMapping("/agent/edit/*")
    public String handleEdit(
            @ModelAttribute("agent") @Valid Agent agent, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("agent", agent);
            return "agent-form";
        }

        Result<Agent> serviceResult = service.update(agent);

        if (!serviceResult.isSuccess()) {
            for (String error : serviceResult.getMessages()) {
                result.addError(new ObjectError("agent", error));
            }
            model.addAttribute("agent", agent);
            return "agent-form";
        }

        return "redirect:/agent";
    }

    @GetMapping("/agent/delete/{agentId}")
    public String displayDelete(@PathVariable int agentId, Model model) {
        Agent agent = service.findById(agentId);

        if (agent == null) {
            return "not-found";
        }

        model.addAttribute("agent", agent);
        return "agent-delete";
    }

    @PostMapping("/agent/delete/{agentId}")
    public String handleDelete(@PathVariable int agentId) {
        service.deleteById(agentId);
        return "redirect:/agent";
    }
}
