package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.service.AccidentService;
import ru.job4j.ru.job4j.accident.model.Accident;

import java.util.List;
import java.util.Map;

@Controller
public class IndexControl {
    private final AccidentService service;

    public IndexControl(AccidentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model) {
        service.addAccident(new Accident(1, "name1", "text1", "address1"));
        service.addAccident(new Accident(2, "name2", "text2", "address2"));
        service.addAccident(new Accident(3, "name3", "text3", "address3"));
        Map<Integer, Accident> accidentMap = service.findAll();
        model.addAttribute("accidentMap", accidentMap);
        return "index";
    }
}