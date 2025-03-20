package my.finance.repository;

import my.finance.models.BudgetCategory;
import my.finance.models.User;
import my.finance.models.Wallet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Integer> {


    public List<BudgetCategory> findAllByWalletAndIsIncome(Wallet wallet, boolean isIncome);

    public default List<BudgetCategory> findAllUserTypedCategories(User user, boolean isIncome) {
        return findAllByWalletAndIsIncome(user.getWallet(), isIncome);
    }

}
