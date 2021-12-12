package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.ru.job4j.accident.model.Accident;

import java.util.HashMap;

@Repository
public class AccidentMem {
    private HashMap<Integer, Accident> accidents = new HashMap<>();

    public void add(Accident accident) {
        accidents.put(accident.getId(), accident);
    }

    public Accident get(int id) {
        return accidents.get(id);
    }

    public HashMap<Integer, Accident> getAll() {
        return accidents;
    }
}
