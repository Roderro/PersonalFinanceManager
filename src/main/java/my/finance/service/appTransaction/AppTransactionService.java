package my.finance.service.appTransaction;

import my.finance.exception.appTransaction.AppTransactionCreatedException;
import my.finance.exception.appTransaction.AppTransactionNotFoundException;
import my.finance.exception.budgetCategory.BudgetCategoryNotFoundException;
import my.finance.exception.user.UserCreatedException;
import my.finance.models.AppTransaction;
import my.finance.models.User;
import my.finance.models.Wallet;
import my.finance.repository.AppTransactionRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppTransactionService {

    private final AppTransactionRepository appTransactionRepository;

    public AppTransactionService(AppTransactionRepository appTransactionRepository) {
        this.appTransactionRepository = appTransactionRepository;
    }


    public AppTransaction save(AppTransaction newAppTransaction) {
        try {
            return appTransactionRepository.save(newAppTransaction);
        } catch (DataAccessException e) {
            throw new AppTransactionCreatedException("Ошибка создания транзакции");
        }
    }

    public double sumByCategory(int id) {
        double sum = 0;
        try {
            sum = appTransactionRepository.sumByCategory(id);
        } catch (DataRetrievalFailureException e) {
            throw new BudgetCategoryNotFoundException("Категория с " + id + "не найдена");
        }
        return sum;
    }

    public List<AppTransaction> findAllByWallet(Wallet wallet) {
        return appTransactionRepository.findAllByWallet(wallet);
    }

    public void delete(AppTransaction delAppTransaction) {
        try {
            appTransactionRepository.delete(delAppTransaction);
        } catch (DataRetrievalFailureException e) {
            throw new AppTransactionNotFoundException("Транзакция " + delAppTransaction.getDescription() + " не найдена");
        } catch (DataAccessException e) {
            throw new AppTransactionCreatedException(
                    "Не удалось удалить транзакцию " + delAppTransaction.getDescription() + " " + e.getMessage());
        }
    }

    public List<AppTransaction> findAllByBudgetCategory_Id(int id) {
        return appTransactionRepository.findAllByBudgetCategory_Id(id);
    }

    public List<Object[]> selectIncomeAndAggregateAmount(Wallet wallet) {
        return appTransactionRepository.selectIncomeAndAggregateAmount(wallet);
    }

    public List<Object[]> selectExpensesAndCalRemainder(Wallet wallet) {
        return appTransactionRepository.selectExpensesAndCalRemainder(wallet);
    }

    public double sumByTypedCategoriesForPeriod(Wallet wallet, boolean b, LocalDate startDate, LocalDate endDate) {
        return appTransactionRepository.sumByTypedCategoriesForPeriod(wallet, b, startDate, endDate);
    }

    public double sumByTypedCategories(User user, boolean isIncome) {
        return appTransactionRepository.sumByTypedCategories(user, isIncome);
    }

    public List<AppTransaction> getAllForPeriod(Wallet wallet, LocalDate startDate, LocalDate endDate) {
        return appTransactionRepository.getAllForPeriod(wallet, startDate, endDate);
    }
}