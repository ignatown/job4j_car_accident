package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentMem;
import ru.job4j.accident.service.AccidentService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccidentControl {
    private final AccidentMem accidents;
    private final AccidentService service;

    public AccidentControl(AccidentMem accidents) {
        this.accidents = accidents;
        service = new AccidentService(accidents);
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", accidents.getTypes());
        model.addAttribute("rules", accidents.getRulesArray());
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        service.setRuleToAccident(accident, req);
        accidents.add(accident);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("types", accidents.getTypes());
        model.addAttribute("rules", accidents.getRulesArray());
        model.addAttribute("accident", accidents.get(id));
        return "accident/edit";
    }
}