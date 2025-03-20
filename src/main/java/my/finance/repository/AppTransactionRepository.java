package my.finance.repository;

import my.finance.models.AppTransaction;
import my.finance.models.User;
import my.finance.models.Wallet;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public interface AppTransactionRepository extends JpaRepository<AppTransaction, Integer> {

    List<AppTransaction> findAllByWallet(Wallet wallet);

    List<AppTransaction> findAllByBudgetCategory_Id(int categoryID);

    @Query("select coalesce(sum(ap.amount),0) from AppTransaction ap where ap.budgetCategory.id=:categoryId")
    Double sumByCategory(@Param("categoryId") int categoryId);

    @Query("SELECT ap.budgetCategory.categoryName, SUM(ap.amount)" +
            "FROM AppTransaction ap " +
            "WHERE ap.wallet.id = :walletId " +
            "AND ap.budgetCategory.isIncome = true " +
            "GROUP BY ap.budgetCategory.categoryName")
    List<Object[]> selectIncomeAndAggregateAmount(@Param("walletId") int walletId);

    public default List<Object[]> selectIncomeAndAggregateAmount(Wallet wallet) {
        return selectIncomeAndAggregateAmount(wallet.getId());
    }

    @Query("SELECT ap.budgetCategory.categoryName, SUM(ap.amount) , ap.budgetCategory.budgetLimit + SUM(ap.amount)" +
            "FROM AppTransaction ap " +
            "WHERE ap.wallet.id = :walletId " +
            "AND ap.budgetCategory.isIncome = false " +
            "GROUP BY ap.budgetCategory.categoryName, ap.budgetCategory.budgetLimit")
    List<Object[]> selectExpensesAndCalRemainder(@Param("walletId") int walletId);

    public default List<Object[]> selectExpensesAndCalRemainder(Wallet wallet) {
        return selectExpensesAndCalRemainder(wallet.getId());
    }

    @Query("SELECT SUM(ap.amount) " +
            "FROM AppTransaction ap " +
            "WHERE ap.wallet.id = :walletId " +
            "AND ap.budgetCategory.isIncome = :isIncome")
    Double sumByTypedCategories(@Param("walletId") int walletId, boolean isIncome);

    public default Double sumByTypedCategories(User user, boolean isIncome) {
        return sumByTypedCategories(user.getWallet(), isIncome);
    }

    public default Double sumByTypedCategories(Wallet wallet, boolean isIncome) {
        return sumByTypedCategories(wallet.getId(), isIncome);
    }

    @Query("SELECT SUM(ap.amount) " +
            "FROM AppTransaction ap " +
            "WHERE ap.wallet.id = :walletId " +
            "AND ap.budgetCategory.isIncome = :isIncome " +
            "AND ap.updatedAt >= :startDate " +
            "AND ap.updatedAt <= :endDate")
    Double sumByTypedCategoriesForPeriod(int walletId, boolean isIncome, LocalDateTime startDate, LocalDateTime endDate);

    public default Double sumByTypedCategoriesForPeriod(Wallet wallet, boolean isIncome, LocalDate startDate, LocalDate endDate) {
        return sumByTypedCategoriesForPeriod(wallet.getId(), isIncome, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
    }

    @Query("FROM AppTransaction ap " +
            "WHERE ap.wallet.id = :walletId " +
            "AND ap.updatedAt >= :startDate " +
            "AND ap.updatedAt <= :endDate")
    List<AppTransaction> getAllForPeriod(int walletId, LocalDateTime startDate, LocalDateTime endDate);

    public default List<AppTransaction> getAllForPeriod(Wallet wallet, LocalDate startDate, LocalDate endDate) {
        return getAllForPeriod(wallet.getId(), startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
    }
}
