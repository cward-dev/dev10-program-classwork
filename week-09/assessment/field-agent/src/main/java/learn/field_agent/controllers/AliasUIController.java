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
import java.util.List;

@Controller
public class AliasUIController {

    private final AliasService aliasService;
    private final AgentService agentService;

    public AliasUIController(AliasService aliasService, AgentService agentService) {
        this.aliasService = aliasService;
        this.agentService = agentService;
    }

    @GetMapping("/agent/{agentId}/alias")
    public String displayAll(@PathVariable int agentId, Model model) {
        Agent agent = agentService.findById(agentId);

        if (agent == null) {
            return "not-found";
        }

        model.addAttribute("agent", agent);
        model.addAttribute("aliases", aliasService.findByAgentId(agentId));
        return "alias-display";
    }

    @GetMapping("/agent/{agentId}/alias/add")
    public String displayAdd(@ModelAttribute("alias") Alias alias, @PathVariable int agentId, Model model) {
        model.addAttribute("agentId", agentId);
        return "alias-form";
    }

    @PostMapping("/agent/{agentId}/alias/add")
    public String handleAdd(
            @ModelAttribute("alias") @Valid Alias alias, BindingResult result,
            @PathVariable int agentId, Model model) {

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

        return String.format("redirect:/agent/%s/alias", agentId);
    }

    @GetMapping("/agent/{agentId}/alias/add/random")
    public String displayAddRandom(@PathVariable int agentId, Model model) {
        Alias alias = aliasService.makeRandomAlias(agentId);
        Result<Alias> result = aliasService.add(alias);

        if (!result.isSuccess()) {
            model.addAttribute("agentId", agentId);
            model.addAttribute("alias", alias);
            return "alias-form";
        }

        return String.format("redirect:/agent/%s/alias", agentId);
    }

    @PostMapping("/agent/{agentId}/alias/add/random")
    public String handleAddRandom(
            @ModelAttribute("alias") @Valid Alias alias, BindingResult result,
            @PathVariable int agentId, Model model) {

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

        return String.format("redirect:/agent/%s/alias", agentId);
    }

    @GetMapping("/agent/{agentId}/alias/edit/{aliasId}")
    public String displayEdit(@PathVariable int agentId, @PathVariable int aliasId, Model model) {
        Alias alias = aliasService.findById(aliasId);
        if (alias == null) {
            return "not-found";
        }

        Agent agent = agentService.findById(agentId);
        if (agent == null) {
            return "not-found";
        }

        model.addAttribute("alias", alias);
        model.addAttribute("agentId", agentId);
        return "alias-form";
    }

    @PostMapping("/agent/{agentId}/alias/edit/*")
    public String handleEdit(
            @ModelAttribute("alias") @Valid Alias alias, BindingResult result,
            @PathVariable int agentId, Model model) {

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

        return String.format("redirect:/agent/%s/alias", agentId);
    }

    @GetMapping("/agent/{agentId}/alias/delete/{aliasId}")
    public String displayDelete(@PathVariable int agentId, @PathVariable int aliasId, Model model) {
        Alias alias = aliasService.findById(aliasId);

        if (alias == null) {
            return "not-found";
        }

        model.addAttribute("alias", alias);
        return "alias-delete";
    }

    @PostMapping("/agent/{agentId}/alias/delete/{aliasId}")
    public String handleDelete(@PathVariable int agentId, @PathVariable int aliasId) {
        aliasService.deleteById(aliasId);
        return String.format("redirect:/agent/%s/alias", agentId);
    }
}
