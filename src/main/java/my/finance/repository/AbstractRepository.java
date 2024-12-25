package my.finance.repository;

import my.finance.Utils.HibernateUtil;
import my.finance.models.AppTransaction;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class AbstractRepository<T> implements Repository<T> {
    private final Class<T> clazz;
    protected static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public AbstractRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Optional<T> getByID(int key) throws HibernateException {
        T foundItem = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            foundItem = session.get(clazz, key);
            session.getTransaction().commit();
        }
        return Optional.ofNullable(foundItem);
    }

    @Override
    public void add(T item) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(item);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(T item) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<T> update(T item) throws HibernateException {
        T updateItem = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            updateItem = session.merge(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return Optional.ofNullable(updateItem);
    }

    @Override
    public void deleteByID(int key) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Optional<T> item = getByID(key);
            if (item.isPresent()) {
                session.remove(item);
            }
            session.getTransaction().commit();
        }
    }
}



