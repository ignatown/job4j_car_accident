package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.repository.AccidentMem;
import ru.job4j.di.Store;
import ru.job4j.ru.job4j.accident.model.Accident;

import java.util.Map;

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

    public Map<Integer, Accident> findAll() {
        return accidentMem.getAll();
    }
}
