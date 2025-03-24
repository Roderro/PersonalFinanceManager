package my.finance.service.budgetCategory;


import my.finance.exception.budgetCategory.BudgetCategoryAlreadyExistsException;
import my.finance.exception.budgetCategory.BudgetCategoryCreatedException;
import my.finance.exception.budgetCategory.BudgetCategoryNotFoundException;
import my.finance.models.BudgetCategory;
import my.finance.repository.BudgetCategoryRepository;
import my.finance.security.AppSession;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BudgetCategoryService {
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final AppSession appSession;

    public BudgetCategoryService(BudgetCategoryRepository budgetCategoryRepository, AppSession appSession) {
        this.budgetCategoryRepository = budgetCategoryRepository;
        this.appSession = appSession;
    }


    public BudgetCategory save(BudgetCategory budgetCategory) {
        try {
            return budgetCategoryRepository.save(budgetCategory);
        } catch (DataIntegrityViolationException e) {
            throw new BudgetCategoryAlreadyExistsException("Данная категория уже существует");
        } catch (DataAccessException e) {
            throw new BudgetCategoryCreatedException("Ошибка создания категории");
        }
    }

    @Transactional(readOnly = true)
    public BudgetCategory getBudgetCategoryByName(String budgetCategoryName) {
        return budgetCategoryRepository.findByCategoryName(budgetCategoryName)
                .orElseThrow(() -> new BudgetCategoryNotFoundException("Категория " + budgetCategoryName + " не найдена"));
    }

    @Transactional(readOnly = true)
    public List<BudgetCategory> findAllUserTypedCategories(boolean isIncome) {
        return budgetCategoryRepository.findAllUserTypedCategories(appSession.getUser(), isIncome);
    }


    public void delete(BudgetCategory delCategory) {
        budgetCategoryRepository.delete(delCategory);
    }

}
