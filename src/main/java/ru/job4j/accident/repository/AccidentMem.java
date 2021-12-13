package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private List<AccidentType> types = new ArrayList<>() {{
            add(AccidentType.of(1, "None"));
            add(AccidentType.of(2, "Two cars"));
            add(AccidentType.of(3, "Car and man"));
            add(AccidentType.of(4, "Car and bike"));
        }};

    private List<Rule> rules = new ArrayList<>() {{
            add(Rule.of(1, "Rule. 1"));
            add(Rule.of(2, "Rule. 2"));
            add(Rule.of(3, "Rule. 3"));
        }};

    private HashMap<Integer, Accident> accidents = new HashMap<>() {{
        Accident accident1 = new Accident("name1", "text1", "address1");
        accident1.setId(1);
        Accident accident2 = new Accident("name2", "text2", "address2");
        accident2.setId(2);
        Accident accident3 = new Accident("name3", "text3", "address3");
        accident3.setId(3);
        put(1, accident1);
        put(2, accident2);
        put(3, accident3);
    }};
    private final AtomicInteger id = new AtomicInteger(4);

    public void add(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(id.getAndIncrement());
        }
        accident.setType(types.get(accident.getType().getId() - 1));
        accidents.put(accident.getId(), accident);
    }

    public List<Rule> getRules() {
        return rules;
    }

    public List<AccidentType> getTypes() {
        return types;
    }

    public Accident get(int id) {
        return accidents.get(id);
    }

    public Collection<Accident> getAll() {
        return accidents.values();
    }
}
