package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.repository.AccidentMem;
import ru.job4j.accident.model.Accident;

import java.util.Collection;

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
}
