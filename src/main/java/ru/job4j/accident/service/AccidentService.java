package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.*;
import ru.job4j.accident.model.Accident;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class AccidentService {
    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;
    private final RuleRepository ruleRepository;

    public AccidentService(AccidentRepository accidentRepository, AccidentTypeRepository accidentTypeRepository, RuleRepository ruleRepository) {
        this.accidentRepository = accidentRepository;
        this.accidentTypeRepository = accidentTypeRepository;
        this.ruleRepository = ruleRepository;
    }

    public Collection<AccidentType> getTypes() {
            List<AccidentType> accidentTypeList = new ArrayList<>();
            accidentTypeRepository.findAll().forEach(accidentTypeList::add);
            return accidentTypeList;
    }

    public Collection<Rule> getRulesArray() {
        List<Rule> rules = new ArrayList<>();
        ruleRepository.findAll().forEach(rules::add);
        return rules;
    }

    @Transactional
    public void add(Accident accident, HttpServletRequest req) {
        for (String s : req.getParameterValues("rIds")) {
            accident.addRule(getRulesMap().get(Integer.parseInt(s)));
        }
     //   if (accident.getId() == 0) {
            accidentRepository.save(accident);
         //   accidents.add(accident);
     //   } else {
         //   accidentRepository.
       //     accidents.update(accident);
     //   }
    }

    public Accident get(int id) {
            return accidentRepository.findAccidentById(id);
    }

    public void delete(int id) {
       accidentRepository.deleteById(id);
    }

    public Collection<Accident> findAll() {
        return accidentRepository.findAllAccident();
    }

    public Map<Integer, Rule> getRulesMap() {
        Map<Integer, Rule> rsl = new HashMap<>();
        for (Rule r: getRulesArray()) {
            rsl.put(r.getId(), r);
        }
        return rsl;
    }
}