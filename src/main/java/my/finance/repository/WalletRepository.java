package my.finance.repository;

import my.finance.Utils.HibernateUtil;
import my.finance.models.AppTransaction;
import my.finance.models.BudgetCategory;
import my.finance.models.User;
import my.finance.models.Wallet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WalletRepository extends AbstractRepository<Wallet> {

    public WalletRepository() {
        super(Wallet.class);
    }

    public List<Wallet> findAll() {
        List<Wallet> resultList = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Query<Wallet> query = session.createQuery("FROM Wallet", Wallet.class);
            resultList = query.list();
        }
        return resultList;
    }


    public void updateBalance(Wallet wallet, double newBalance) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            wallet.setBalance(newBalance);
            session.merge(wallet);
            session.getTransaction().commit();
        }
    }

    public double calculateBalance(int walletId) {
        Double calculatedBalance;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Double> query = session.createQuery(
                    "SELECT SUM(ap.amount) " +
                            "FROM AppTransaction ap " +
                            "WHERE ap.wallet.id = :walletId ", Double.class);
            query.setParameter("walletId", walletId);
            calculatedBalance = query.getSingleResult();
            session.getTransaction().commit();
        }
        return calculatedBalance == null ? 0. : calculatedBalance;
    }
}
