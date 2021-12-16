package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentHibernate;
import ru.job4j.accident.repository.AccidentJdbcTemplate;
import ru.job4j.accident.model.Accident;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class AccidentService {
    private final AccidentHibernate accidents;

    public AccidentService(AccidentHibernate accidents) {
        this.accidents = accidents;
    }

    public Collection<AccidentType> getTypes() {
        return accidents.getTypes();
    }

    public Collection<Rule> getRulesArray() {
        return accidents.getRulesArray();
    }

    public void add(Accident accident) {
        if (accident.getId() == 0) {
            accidents.add(accident);
        } else {
            accidents.update(accident);
        }
    }

    public Accident get(int id) {
        return accidents.get(id);
    }

    public Collection<Accident> findAll() {
        return accidents.getAll();
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