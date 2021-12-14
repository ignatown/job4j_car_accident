package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentMem;
import ru.job4j.accident.model.Accident;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class AccidentService {

    private final AccidentMem accidentMem;

    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }

    public void addAccident(Accident accident) {
        accidentMem.add(accident);
    }

    public Accident getAccident(int id) {
        return accidentMem.get(id);
    }

    public Collection<Accident> findAll() {
        return accidentMem.getAll();
    }

    public void setRuleToAccident(Accident accident, HttpServletRequest req) {
        Set<Rule> rules = new HashSet<>();
        for (String s:req.getParameterValues("rIds")) {
            rules.add(accidentMem.getRulesMap().get(Integer.parseInt(s)));
        }
        accident.setRules(rules);
    }
}
