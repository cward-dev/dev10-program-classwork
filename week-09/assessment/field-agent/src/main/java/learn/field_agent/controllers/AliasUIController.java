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

    @GetMapping("/alias/{agentId}")
    public String displayAll(@PathVariable int agentId, Model model) {
        model.addAttribute("agent", agentService.findById(agentId));
        model.addAttribute("aliases", aliasService.findByAgentId(agentId));
        return "alias-display";
    }

    @GetMapping("/alias/{agentId}/add")
    public String displayAdd(@ModelAttribute("alias") Alias alias, @PathVariable int agentId, Model model) {
        model.addAttribute("agentId", agentId);
        return "alias-form";
    }

    @PostMapping("/alias/{agentId}/add")
    public String handleAdd(
            @ModelAttribute("alias") @Valid Alias alias, @PathVariable int agentId,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("agentId", agentId);
            model.addAttribute("alias", alias);
            return "alias-form";
        }

        Result<Alias> serviceResult = aliasService.add(alias);

        if (!serviceResult.isSuccess()) {
            for (String error : serviceResult.getMessages()) {
                result.addError(new ObjectError("alias", error));
            }
            model.addAttribute("agentId", agentId);
            model.addAttribute("alias", alias);
            return "alias-form";
        }

        return String.format("redirect:/alias/agent/%s", alias.getAgentId());
    }

    @GetMapping("/alias/{agentId}/edit/{aliasId}")
    public String displayEdit(@PathVariable int agentId, @PathVariable int aliasId, Model model) {
        Alias alias = aliasService.findById(aliasId);
        if (alias == null) {
            return "not-found";
        }

        Agent agent = agentService.findById(alias.getAgentId());
        if (agent == null) {
            return "not-found";
        }

        model.addAttribute("alias", alias);
        model.addAttribute("agentId", alias.getAgentId());
        return "alias-form";
    }

    @PostMapping("/alias/{agentId}/edit/*")
    public String handleEdit(
            @ModelAttribute("alias") @Valid Alias alias, @PathVariable int agentId,
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

        return String.format("redirect:/alias/%s", alias.getAgentId());
    }

    @GetMapping("/alias/{agentId}/delete/{aliasId}")
    public String displayDelete(@PathVariable int agentId, @PathVariable int aliasId, Model model) {
        Alias alias = aliasService.findById(aliasId);

        if (alias == null) {
            return "not-found";
        }

        model.addAttribute("alias", alias);
        return "alias-delete";
    }

    @PostMapping("/alias/{agentId}/delete/{aliasId}")
    public String handleDelete(@PathVariable int agentId, @PathVariable int aliasId, Model model) {
        aliasService.deleteById(aliasId);
        return String.format("redirect:/alias/%s", agentId);
    }
}
