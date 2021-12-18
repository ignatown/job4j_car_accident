package ru.job4j.accident.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.accident.model.Accident;

import java.util.List;

public interface AccidentRepository extends CrudRepository<Accident, Integer> {

    @Query("select distinct a from Accident a left join fetch a.rules left join fetch a.type")
    List<Accident> findAllAccident();

    @Query("select distinct a from Accident a left join fetch a.rules left join fetch a.type where a.id =:id")
    Accident findAccidentById(@Param("id") int id);
}