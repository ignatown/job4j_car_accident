package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentJdbcTemplate;
import ru.job4j.accident.model.Accident;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class AccidentService {
    private final AccidentJdbcTemplate accidentJdbcTemplate;
    public AccidentService(AccidentJdbcTemplate accidentJdbcTemplate) {
        this.accidentJdbcTemplate = accidentJdbcTemplate;
    }

    public Collection<AccidentType> getTypes() {
        return accidentJdbcTemplate.getTypes();
    }

    public Collection<Rule> getRulesArray() {
        return accidentJdbcTemplate.getRulesArray();
    }

    public void add(Accident accident) {
        accidentJdbcTemplate.add(accident);
    }

    public Accident get(int id) {
        return accidentJdbcTemplate.get(id);
    }

    public Collection<Accident> findAll() {
        return accidentJdbcTemplate.getAll();
    }

    public void setRuleToAccident(Accident accident, HttpServletRequest req) {
        Set<Rule> rules = new HashSet<>();
        for (String s : req.getParameterValues("rIds")) {
            rules.add(getRulesMap().get(Integer.parseInt(s)));
        }
        accident.setRules(rules);
    }

    public Map<Integer, Rule> getRulesMap() {
        Map<Integer, Rule> rsl = new HashMap<>();
        for (Rule r: getRulesArray()) {
            rsl.put(r.getId(), r);
        }
        return rsl;
    }
}