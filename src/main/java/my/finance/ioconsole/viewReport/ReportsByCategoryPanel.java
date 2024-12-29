package my.finance.ioconsole.viewReport;

import my.finance.ioconsole.AbstractPanel;
import my.finance.models.BudgetCategory;
import my.finance.security.AppSession;
import org.hibernate.HibernateException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Stream;

public class ReportsByCategoryPanel extends AbstractPanel {
    static final String TEXT = "Отчет по категориям";

    public ReportsByCategoryPanel(AppSession appSession) {
        super(appSession);
    }

    @Override
    public void action() {
        List<BudgetCategory> incomeCategories = budgetCategoryRepository
                .findAllUserTypedCategories(appSession.getUser(), true);
        List<BudgetCategory> expenseCategories = budgetCategoryRepository
                .findAllUserTypedCategories(appSession.getUser(), false);
        output.fPrintCategories(incomeCategories, expenseCategories);
        List<BudgetCategory> userCategories = Stream.concat(incomeCategories.stream(), expenseCategories.stream())
                .toList();
        if (userCategories.isEmpty()) return;
        output.print("Введите № категории:");
        try {
            int inputNum = input.nextInt();
            BudgetCategory category = userCategories.get(inputNum - 1);
            double summaTransactions = appTransactionRepository.sumByCategory(category.getId());
            if (category.isIncome()) {
                output.printf("Общий доход по категории: %.2f %n", summaTransactions);
            } else {
                output.printf("Общий расход по категории: %s, остаток по лимиту:%.2f %n", summaTransactions,
                        (category.getBudgetLimit() + summaTransactions));
            }
            output.fPrintAppTransaction(appTransactionRepository.getByCategory(category.getId()));
            waitEnter();
        } catch (InputMismatchException e) {
            output.println("Введено не число!");
        } catch (IndexOutOfBoundsException e) {
            output.println("Введено неправильное число!");
        } catch (HibernateException e) {
            output.println("Ошибка чтения!");
        }
    }
}

