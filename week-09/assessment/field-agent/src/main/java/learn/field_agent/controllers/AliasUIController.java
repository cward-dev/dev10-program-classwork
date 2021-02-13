package learn.field_agent.controllers;

import learn.field_agent.domain.AgentService;
import learn.field_agent.domain.AliasService;
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

@Controller
public class AliasUIController {

    private final AliasService aliasService;
    private final AgentService agentService;

    public AliasUIController(AliasService aliasService, AgentService agentService) {
        this.aliasService = aliasService;
        this.agentService = agentService;
    }

    @GetMapping("/alias/agent/{agentId}")
    public String displayAll(@PathVariable int agentId, Model model) {
        model.addAttribute("agent", agentService.findById(agentId));
        model.addAttribute("aliases", aliasService.findByAgentId(agentId));
        return "alias-display";
    }

    @GetMapping("/alias/add/agent/{agentId}")
    public String displayAdd(@PathVariable int agentId, @ModelAttribute("alias") Alias alias, Model model) {
        model.addAttribute("agentId", agentId);
        return "alias-form";
    }

    @PostMapping("/alias/add")
    public String handleAdd(
            @ModelAttribute("alias") @Valid Alias alias, int agentId,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("alias", alias);
            return "alias-form";
        }

        alias.setAgentId(agentId);

        Result<Alias> serviceResult = aliasService.add(alias);

        if (!serviceResult.isSuccess()) {
            for (String error : serviceResult.getMessages()) {
                result.addError(new ObjectError("alias", error));
            }
            model.addAttribute("alias", alias);
            return "alias-form";
        }

        return String.format("redirect:/alias/agent/%s", alias.getAgentId());
    }

    @GetMapping("/alias/edit/{aliasId}")
    public String displayEdit(@PathVariable int aliasId, Model model) {
        Alias alias = aliasService.findById(aliasId);
        if (alias == null) {
            return "not-found";
        }

        Agent agent = agentService.findById(alias.getAgentId());
        if (agent == null) {
            return "not-found";
        }

        model.addAttribute("alias", alias);
        return "alias-form";
    }

    @PostMapping("/alias/edit/*")
    public String handleEdit(
            @ModelAttribute("alias") @Valid Alias alias,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("alias", alias);
            return "alias-form";
        }

        Result<Alias> serviceResult = aliasService.update(alias);

        if (!serviceResult.isSuccess()) {
            for (String error : serviceResult.getMessages()) {
                result.addError(new ObjectError("alias", error));
            }
            model.addAttribute("alias", alias);
            return "alias-form";
        }

        return String.format("redirect:/alias/agent/%s", alias.getAgentId());
    }

    @GetMapping("/alias/delete/{aliasId}")
    public String displayDelete(@PathVariable int aliasId, Model model) {
        Alias alias = aliasService.findById(aliasId);

        if (alias == null) {
            return "not-found";
        }

        model.addAttribute("alias", alias);
        return "alias-delete";
    }

    @PostMapping("/alias/delete/{aliasId}")
    public String handleDelete(@PathVariable int aliasId, Model model) {
        aliasService.deleteById(aliasId);
        return "redirect:/alias";
    }
}
