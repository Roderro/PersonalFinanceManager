package my.finance.exception.budgetCategory;


public class BudgetCategoryNotFoundException extends RuntimeException {
    public BudgetCategoryNotFoundException(String message) {
        super(message);
    }
}
