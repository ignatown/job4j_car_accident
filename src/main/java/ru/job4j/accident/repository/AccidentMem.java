package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AccidentMem {
    private Map<Integer, AccidentType> types = new HashMap<>();
    private Map<Integer, Rule> rules = new HashMap<>();
    private HashMap<Integer, Accident> accidents = new HashMap<>();
    private final AtomicInteger id = new AtomicInteger(4);

    public AccidentMem() {
        types.put(1, AccidentType.of(1, "None"));
        types.put(2, AccidentType.of(2, "Two cars"));
        types.put(3, AccidentType.of(3, "Car and man"));
        types.put(4, AccidentType.of(4, "Car and bike"));
        accidents.put(1, Accident.of(1, "name1", "text1", "address1"));
        accidents.put(2, Accident.of(2, "name2", "text2", "address2"));
        accidents.put(3, Accident.of(3, "name3", "text3", "address3"));
        rules.put(1, Rule.of(1, "Rule. 1"));
        rules.put(2, Rule.of(2, "Rule. 2"));
        rules.put(3, Rule.of(3, "Rule. 3"));
    }

    public void add(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(id.getAndIncrement());
        }
        accident.setType(types.get(accident.getType().getId() - 1));
        accidents.put(accident.getId(), accident);
    }

    public Map<Integer, Rule> getRulesMap() {
        return rules; }

    public Collection<Rule> getRulesArray() {
        return rules.values(); }

    public Collection<AccidentType> getTypes() {
        return types.values();
    }

    public Accident get(int id) {
        return accidents.get(id);
    }

    public Collection<Accident> getAll() {
        return accidents.values();
    }
}
