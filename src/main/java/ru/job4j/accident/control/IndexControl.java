package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.service.AccidentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class IndexControl {
    private final AccidentService service;

    public IndexControl(AccidentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("accidentList", service.findAll());
        return "index";
    }
}