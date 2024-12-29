package my.finance.repository;

import my.finance.models.BudgetCategory;
import my.finance.models.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class BudgetCategoryRepository extends AbstractRepository<BudgetCategory> {

    public BudgetCategoryRepository() {
        super(BudgetCategory.class);
    }

    public List<BudgetCategory> findAllUserTypedCategories(User user, boolean isIncome) {
        List<BudgetCategory> resultList = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<BudgetCategory> query = session.createQuery(
                    "FROM BudgetCategory bc " +
                            "WHERE bc.wallet.owner.id = :userId " +
                            "AND bc.isIncome = :isIncome", BudgetCategory.class);
            query.setParameter("userId", user.getId());
            query.setParameter("isIncome", isIncome);
            resultList = new ArrayList<>(query.getResultList());
            session.getTransaction().commit();
        }
        return resultList == null ? new ArrayList<>() : resultList;
    }

    public List<BudgetCategory> findAllUserCategories(User user) {
        List<BudgetCategory> resultList = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            resultList = new ArrayList<>(session.get(User.class, user.getId()).getWallet().getBudgetCategories());
            session.getTransaction().commit();
        }
        return resultList == null ? new ArrayList<>() : resultList;
    }


}
