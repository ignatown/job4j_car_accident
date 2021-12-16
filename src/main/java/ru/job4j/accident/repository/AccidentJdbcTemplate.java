package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

@Repository
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void add(Accident accident) {
        if (accident.getId() == 0) {
            save(accident);
        } else {
            update(accident);
        }
    }

    private Accident save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement statement
                    = connection.prepareStatement("INSERT INTO accident(name, text, address, accident_type_id) "
                    + "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, accident.getName());
            statement.setString(2, accident.getText());
            statement.setString(3, accident.getAddress());
            statement.setInt(4, accident.getType().getId());
            return statement;
        }, keyHolder);
        accident.setId((int) keyHolder.getKeys().get("id"));
        createRules(accident);
        return accident;
    }

    private Accident update(Accident accident) {
        jdbc.update("update accident set name = ?, text = ?, address = ?, accident_type_id = ?"
                        + " where id = ?",
                accident.getName(), accident.getText(), accident.getAddress(),
                accident.getType().getId(), accident.getId());
        updateRules(accident);
        return accident;
    }

    private void createRules(Accident accident) {
        for (Rule r : accident.getRules()) {
            jdbc.update("INSERT INTO accident_rules(accident_id, rule_id) "
                    + "VALUES (?, ?)", accident.getId(), r.getId());
        }
    }

    private void updateRules(Accident accident) {
        jdbc.update("DELETE FROM accident_rules WHERE accident_id = ?",
                accident.getId());
        createRules(accident);
    }

    public List<Accident> getAll() {
        return jdbc.query("select a.id as id, a.name as name, a.text as text, a.address as address, a.accident_type_id as type_id, "
                        + "t.name as type_name from accident as a left join types as t on a.accident_type_id = t.id",
                (rs, row) -> {
                    Accident accident = new Accident(
                            rs.getString("name"),
                            rs.getString("text"),
                            rs.getString("name")
                    );
                    accident.setId(rs.getInt("id"));
                    accident.setType(AccidentType.of(rs.getInt("type_id"), rs.getString("type_name")
                    ));
                    rulesForAccidentById(accident.getId()).forEach(accident::addRule);
                    return accident;
                });
    }

    private Collection<Rule> rulesForAccidentById(int id) {
        Object[] args = {id};
        return jdbc.query("select r.id as id, r.name as name from rules as r"
                        + " join accident_rules as a on r.id = a.rule_id where a.accident_id = ?", args,
                (rs, row) -> Rule.of(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
    }

    public Accident get(int id) {
        final Object[] args = {id};
        Accident accident = jdbc.queryForObject("select a.id as id, a.name as name, a.text as text, a.address as address, a.accident_type_id as type_id, "
                        + "t.name as type_name from accident as a left join types as t on a.accident_type_id = t.id where a.id = ?", args,
                (rs, row) -> {
                    Accident ac = new Accident(
                            rs.getString("name"),
                            rs.getString("text"),
                            rs.getString("address")
                    );
                    ac.setType(AccidentType.of(
                            rs.getInt("type_id"),
                            rs.getString("type_name")
                    ));
                    ac.setId(rs.getInt("id"));
                    rulesForAccidentById(ac.getId()).forEach(ac::addRule);
                    return ac;
                });
        return accident;
    }


    public Collection<AccidentType> getTypes() {
        return jdbc.query("select * from types",
                (rs, row) -> AccidentType.of(
                        rs.getInt("id"),
                        rs.getString("name")
                )
        );
    }

    public Collection<Rule> getRulesArray() {
        return jdbc.query("SELECT * FROM rules",
                (rs, row) ->
                        Rule.of(
                                rs.getInt("id"),
                                rs.getString("name")
                        )
        );
    }
}