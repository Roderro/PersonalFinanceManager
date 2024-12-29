package my.finance.repository;

import jakarta.persistence.TypedQuery;
import my.finance.models.User;
import my.finance.models.Wallet;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository extends AbstractRepository<User> {


    public UserRepository() {
        super(User.class);
    }

    public Optional<User> findByLogin(String login) throws HibernateException {
        List<User> foundUser = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<User> query = session.createQuery(
                    "SELECT u FROM User u WHERE u.login = :login", User.class);
            query.setParameter("login", login);
            foundUser = query.getResultList();
        }
        return foundUser.isEmpty() ? Optional.empty() : Optional.of(foundUser.getFirst());
    }

    public void updateBalance(User user, double newBalance) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Wallet wallet = user.getWallet();
            wallet.setBalance(newBalance);
            session.merge(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}


