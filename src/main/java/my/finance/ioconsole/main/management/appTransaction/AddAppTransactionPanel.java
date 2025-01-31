package my.finance.ioconsole.main.management.appTransaction;

import my.finance.ioconsole.main.management.category.AddCategoryPanel;
import my.finance.models.AppTransaction;
import my.finance.models.BudgetCategory;
import my.finance.security.AppSession;
import org.hibernate.HibernateException;

import java.util.InputMismatchException;
import java.util.List;

public class AddAppTransactionPanel extends AddCategoryPanel {
    static final String TEXT = "Добавление транзакции";

    public AddAppTransactionPanel(AppSession appSession) {
        super(appSession);
    }

    @Override
    public void action() {
        try {
            boolean isIncome = getTypeCategory();
            BudgetCategory selectedCategory = getUserTypedCategory(isIncome);
            double SumAppTransaction = getSumAppTransaction();
            String inputDescription = getDescription();
            AppTransaction newAppTransaction = new AppTransaction(appSession.getWallet(), SumAppTransaction,
                    selectedCategory, inputDescription);
            addAppTransaction(newAppTransaction);
            if (!isIncome) {
                double sumByCategory = appTransactionRepository.sumByCategory(selectedCategory.getId());
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
            appTransactionRepository.add(newAppTransaction);
        } catch (HibernateException e) {
            throw new RuntimeException("Не удалось добавить транзакцию, попробуйте снова!");
        }
    }


    private BudgetCategory getUserTypedCategory(boolean isIncome) {
        List<BudgetCategory> userCategories = budgetCategoryRepository.findAllUserTypedCategories(appSession.getUser(), isIncome);
        String stringUserCategory = output.ListCategoriesTofString(userCategories, 0, isIncome);
        String type = isIncome ? "дохода" : "расхода";
        output.printf("""
                Выберете категорию транзакции %s:
                %s
                %d. Создать новую категорию %s
                Введите число:\s""", type, stringUserCategory, userCategories.size() + 1, type);
        int numCategory;
        try {
            numCategory = input.nextInt() - 1;
            if (numCategory > userCategories.size() || numCategory < 0) throw new InputMismatchException();
            if (numCategory == userCategories.size()) {
                return createCategory(isIncome);
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

