package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
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
        accidents.put(accident.getId(), accident);
    }

    public Accident get(int id) {
        return accidents.get(id);
    }

    public Collection<Accident> getAll() {
        return accidents.values();
    }
}
