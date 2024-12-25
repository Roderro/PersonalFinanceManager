package my.finance.repository;

import my.finance.models.AppTransaction;
import my.finance.models.User;
import my.finance.models.Wallet;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppTransactionRepository extends AbstractRepository<AppTransaction> {

    public AppTransactionRepository() {
        super(AppTransaction.class);
    }


    public List<AppTransaction> findAll(User user) {
        return findAll(user.getWallet());
    }

    public List<AppTransaction> findAll(Wallet wallet) {
        List<AppTransaction> resultList;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<AppTransaction> query = session.createQuery("FROM AppTransaction ap " +
                    "WHERE ap.wallet.id = :walletId", AppTransaction.class);
            query.setParameter("walletId", wallet.getId());
            resultList = query.list();
            session.getTransaction().commit();
        }
        return resultList;
    }

    public Double sumByCategory(int categoryID) {
        double result;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Double> query = session.createQuery(
                    "SELECT SUM(ap.amount) " +
                            "FROM AppTransaction ap " +
                            "WHERE ap.budgetCategory.id = :categoryID", Double.class);
            query.setParameter("categoryID", categoryID);
            result = query.getSingleResult();
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            result = 0.;
        }
        return result;
    }


    public List<Object[]> selectIncomeAndAggregateAmount(Wallet wallet) {
        List<Object[]> resultList;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Object[]> query = session.createQuery("SELECT ap.budgetCategory.categoryName, SUM(ap.amount)" +
                    "FROM AppTransaction ap " +
                    "WHERE ap.wallet.id = :walletId " +
                    "AND ap.budgetCategory.isIncome = true " +
                    "GROUP BY ap.budgetCategory.categoryName", Object[].class);
            query.setParameter("walletId", wallet.getId());
            resultList = query.getResultList();
            session.getTransaction().commit();
        }
        return resultList;
    }


    public List<Object[]> selectExpensesAndCalRemainder(Wallet wallet) {
        List<Object[]> resultList;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Object[]> query = session.createQuery(
                    "SELECT ap.budgetCategory.categoryName, ap.budgetCategory.budgetLimit , ap.budgetCategory.budgetLimit + SUM(ap.amount)" +
                            "FROM AppTransaction ap " +
                            "WHERE ap.wallet.id = :walletId " +
                            "AND ap.budgetCategory.isIncome = false " +
                            "GROUP BY ap.budgetCategory.categoryName, ap.budgetCategory.budgetLimit", Object[].class);
            query.setParameter("walletId", wallet.getId());
            resultList = query.getResultList();
            session.getTransaction().commit();
        }
        return resultList;
    }


    public Double sumByTypedCategories(User user, boolean isIncome) {
        return sumByTypedCategories(user.getWallet(), isIncome);
    }

    public Double sumByTypedCategories(Wallet wallet, boolean isIncome) {
        Double result;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Double> query = session.createQuery(
                    "SELECT SUM(ap.amount) " +
                            "FROM AppTransaction ap " +
                            "WHERE ap.wallet.id = :walletId " +
                            "AND ap.budgetCategory.isIncome = :isIncome", Double.class);
            query.setParameter("walletId", wallet.getId());
            query.setParameter("isIncome", isIncome);
            result = query.getSingleResult();
            session.getTransaction().commit();
        }
        return result == null ? 0. : result;
    }

    public Double sumByTypedCategoriesForPeriod(Wallet wallet, boolean isIncome, LocalDate startDate, LocalDate endDate) {
        Double result;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Double> query = session.createQuery(
                    "SELECT SUM(ap.amount) " +
                            "FROM AppTransaction ap " +
                            "WHERE ap.wallet.id = :walletId " +
                            "AND ap.budgetCategory.isIncome = :isIncome " +
                            "AND ap.updatedAt >= :startDate " +
                            "AND ap.updatedAt <= :endDate", Double.class);
            query.setParameter("walletId", wallet.getId());
            query.setParameter("isIncome", isIncome);
            query.setParameter("startDate", startDate.atStartOfDay());
            query.setParameter("endDate", endDate.atTime(LocalTime.MAX));
            result = query.getSingleResult();
            session.getTransaction().commit();
        }
        return result == null ? 0.0 : result;
    }

    public List<AppTransaction> getAllForPeriod(Wallet wallet, LocalDate startDate, LocalDate endDate) {
        List<AppTransaction> result;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<AppTransaction> query = session.createQuery(
                    "FROM AppTransaction ap " +
                            "WHERE ap.wallet.id = :walletId " +
                            "AND ap.updatedAt >= :startDate " +
                            "AND ap.updatedAt <= :endDate", AppTransaction.class);
            query.setParameter("walletId", wallet.getId());
            query.setParameter("startDate", startDate.atStartOfDay());
            query.setParameter("endDate", endDate.atTime(LocalTime.MAX));
            result = query.getResultList();
            session.getTransaction().commit();
        }
        return result;
    }


    public List<AppTransaction> getByCategory(int categoryID) {
        List<AppTransaction> result;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<AppTransaction> query = session.createQuery(
                    "FROM AppTransaction ap " +
                            "WHERE ap.budgetCategory.id = :categoryID", AppTransaction.class);
            query.setParameter("categoryID", categoryID);
            result = query.list();
            session.getTransaction().commit();
            return result;
        }
    }


}
