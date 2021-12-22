package ru.job4j.accident.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accident.model.User;

import java.util.Collection;

public interface UserRepository extends CrudRepository<User, Integer> {
    Collection<User> findUsersByUsername(String name);
}