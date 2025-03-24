package my.finance.ioconsole.main.management.appTransaction;

import my.finance.exception.appTransaction.AppTransactionCreatedException;
import my.finance.ioconsole.AbstractPanel;
import my.finance.ioconsole.main.management.category.AddCategoryPanel;
import my.finance.models.AppTransaction;
import my.finance.models.BudgetCategory;
import my.finance.service.appTransaction.AppTransactionService;
import my.finance.service.budgetCategory.BudgetCategoryService;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;

@Component
@Lazy
@Profile("console")
public class AddAppTransactionPanel extends AbstractPanel {
    static final String TEXT = "Добавление транзакции";
    private final AddCategoryPanel addCategoryPanel;
    private final AppTransactionService appTransactionService;
    private final BudgetCategoryService budgetCategoryService;

    public AddAppTransactionPanel(AddCategoryPanel addCategoryPanel, AppTransactionService appTransactionService, BudgetCategoryService budgetCategoryService) {
        super();
        this.addCategoryPanel = addCategoryPanel;
        this.appTransactionService = appTransactionService;
        this.budgetCategoryService = budgetCategoryService;
    }


    @Override
    public void action() {
        try {
            boolean isIncome = addCategoryPanel.getTypeCategory();
            BudgetCategory selectedCategory = getUserTypedCategory(isIncome);
            double SumAppTransaction = getSumAppTransaction();
            String inputDescription = getDescription();
            AppTransaction newAppTransaction = new AppTransaction(appSession.getWallet(), SumAppTransaction,
                    selectedCategory, inputDescription);
            addAppTransaction(newAppTransaction);
            if (!isIncome) {
                double sumByCategory = appTransactionService.sumByCategory(selectedCategory.getId());
                double remains = selectedCategory.getBudgetLimit() + sumByCategory;
                if (remains < 0) {
                    output.printf("!!!Внимание вы превысили лимит по категории %s на %.2f!%n",
                            selectedCategory.getCategoryName(), Math.abs(remains));
                }
                waitEnter();
            }
        } catch (RuntimeException e) {
            output.println(e.getMessage());
        }
    }

    private void addAppTransaction(AppTransaction newAppTransaction) {
        try {
            appTransactionService.save(newAppTransaction);
        } catch (AppTransactionCreatedException e) {
            throw new RuntimeException("Не удалось создать транзакцию");
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка: " + e.getMessage());
        }
    }


    private BudgetCategory getUserTypedCategory(boolean isIncome) {
        List<BudgetCategory> userCategories = budgetCategoryService.findAllUserTypedCategories(isIncome);
        String type = isIncome ? "дохода" : "расхода";
        output.println("Выберете категорию транзакции %s:".formatted(type));
        output.fPrintListCategories(userCategories, 0, isIncome);
        output.printf("""
                %d. Создать новую категорию %s
                Введите число:\s""", userCategories.size() + 1, type);
        int numCategory;
        try {
            numCategory = input.nextInt() - 1;
            if (numCategory > userCategories.size() || numCategory < 0) throw new InputMismatchException();
            if (numCategory == userCategories.size()) {
                return addCategoryPanel.createCategory(isIncome);
            }
        } catch (InputMismatchException e) {
            throw new RuntimeException("Не удалось выбрать категорию");
        }
        return userCategories.get(numCategory);
    }

    private String getDescription() {
        output.print("Напишите описание к транзакции: ");
        return input.nextLine();
    }

    private Double getSumAppTransaction() throws RuntimeException {
        output.print("Введите сумму транзакции: ");
        double inputDouble;
        try {
            inputDouble = input.nextDouble();
        } catch (InputMismatchException e) {
            throw new RuntimeException("Введено не число");
        }
        return inputDouble;
    }
}

