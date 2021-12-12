package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private HashMap<Integer, Accident> accidents = new HashMap<>();
    private final AtomicInteger id = new AtomicInteger(1);

    public void add(Accident accident) {
        if (!accidents.containsKey(accident.getId())) {
            accident.setId(id.getAndIncrement());
            accidents.put(accident.getId(), accident);
        } else {
            for (Map.Entry<Integer, Accident> acc : accidents.entrySet()) {
                if (acc.getValue().getId() == accident.getId()) {
                    accidents.put(acc.getKey(), accident);
                }
            }
        }
    }

    public Accident get(int id) {
        return accidents.get(id);
    }

    public HashMap<Integer, Accident> getAll() {
        return accidents;
    }
}
