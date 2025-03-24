package my.finance.exception.budgetCategory;

import my.finance.models.BudgetCategory;

public class BudgetCategoryCreatedException extends RuntimeException {
    public BudgetCategoryCreatedException(String message) {
        super(message);
    }
}
