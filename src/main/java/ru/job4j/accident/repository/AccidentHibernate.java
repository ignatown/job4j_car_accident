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

public class AccidentHibernate {

    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T session(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void add(Accident accident) {
            session(session -> session.save(accident));
    }

    public void delete(int id) {
            session(session -> session.createQuery("delete Accident where id=:id")
            .setParameter("id", id)
            .executeUpdate());
    }

    public void update(Accident accident) {
        session(session -> {
            Accident upAccident = session.get(Accident.class, accident.getId());
            upAccident.setName(accident.getName());
            upAccident.setAddress(accident.getAddress());
            upAccident.setText(accident.getText());
            upAccident.setRules(accident.getRules());
            upAccident.setType(accident.getType());
        return true;
        }
        );
    }

    public List<Rule> getRulesArray() {
            return session(session -> session.createQuery("from Rule order by id", Rule.class)
                    .list());
    }

    public Collection<AccidentType> getTypes() {
            return session(session -> session.createQuery("from AccidentType order by id", AccidentType.class)
                    .list());
    }

    public Accident get(int id) {
        return session(session -> session.createQuery("Select distinct a from Accident a left join fetch a.type left join fetch a.rules "
                + "where a.id=:id order by a.id", Accident.class)
                .setParameter("id", id)
                .uniqueResult());
    }

    public Collection<Accident> getAll() {
            return session(session ->  session.createQuery("Select distinct a from Accident a left join fetch a.type left join fetch a.rules "
                    + "order by a.id", Accident.class)
                    .list());
    }
}
