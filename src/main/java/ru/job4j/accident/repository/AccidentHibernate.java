package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Repository
public class AccidentHibernate {

    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    public void add(Accident accident) {
        try (Session session = sf.openSession()) {
            session.save(accident);
        }
    }

    public void update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Accident upAccident = session.get(Accident.class, accident.getId());
            upAccident.setName(accident.getName());
            upAccident.setAddress(accident.getAddress());
            upAccident.setText(accident.getText());
            upAccident.setRules(accident.getRules());
            upAccident.setType(accident.getType());
            session.getTransaction().commit();
        }
    }

    public List<Rule> getRulesArray() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Rule", Rule.class)
                    .list();
        }
    }

    public Collection<AccidentType> getTypes() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from AccidentType", AccidentType.class)
                    .list();
        }
    }

    public Accident get(int id) {
        try (Session session = sf.openSession()) {
        return session.createQuery("Select distinct a from Accident a left join fetch a.type left join fetch a.rules "
                + "where a.id=:id order by a.id", Accident.class)
                .setParameter("id", id)
                .uniqueResult();
        }
    }

    public Collection<Accident> getAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("Select distinct a from Accident a left join fetch a.type left join fetch a.rules "
                    + "order by a.id", Accident.class)
                    .list();
        }
    }
}
