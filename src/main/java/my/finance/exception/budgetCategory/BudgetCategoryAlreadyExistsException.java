package my.finance.exception.budgetCategory;

public class BudgetCategoryAlreadyExistsException extends RuntimeException {
    public BudgetCategoryAlreadyExistsException(String message) {
        super(message);
    }
}
