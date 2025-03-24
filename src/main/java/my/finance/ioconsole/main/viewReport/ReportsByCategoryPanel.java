package my.finance.ioconsole.main.viewReport;

import my.finance.ioconsole.AbstractPanel;
import my.finance.ioconsole.main.management.category.ViewCategoryPanel;
import my.finance.models.AppTransaction;
import my.finance.models.BudgetCategory;
import my.finance.security.AppSession;
import my.finance.service.appTransaction.AppTransactionService;
import org.hibernate.HibernateException;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Stream;

@Component
@Lazy
@Profile("console")
public class ReportsByCategoryPanel extends AbstractPanel {
    static final String TEXT = "Отчет по категориям";
    private final ViewCategoryPanel viewCategoryPanel;
    private final AppTransactionService appTransactionService;

    public ReportsByCategoryPanel(ViewCategoryPanel viewCategoryPanel, AppTransactionService appTransactionService) {
        super();
        this.viewCategoryPanel = viewCategoryPanel;
        this.appTransactionService = appTransactionService;
    }

    @Override
    public void action() {
        List<BudgetCategory> userCategories = viewCategoryPanel.fPrintCategoriesAndReturnConcatCategory();
        if (userCategories.isEmpty()) {
            waitEnter();
            return;
        }
        output.print("Введите № категории:");
        try {
            int inputNum = input.nextInt();
            BudgetCategory category = userCategories.get(inputNum - 1);
            double summaTransactions = appTransactionService.sumByCategory(category.getId());
            if (category.isIncome()) {
                output.printf("Общий доход по категории: %.2f %n", summaTransactions);
            } else {
                output.printf("Общий расход по категории: %s, остаток по лимиту:%.2f %n", summaTransactions,
                        (category.getBudgetLimit() + summaTransactions));
            }
            output.fPrintAppTransaction(appTransactionService.findAllByBudgetCategory_Id(category.getId()));
            waitEnter();
        } catch (InputMismatchException e) {
            output.println("Введено не число!");
        } catch (IndexOutOfBoundsException e) {
            output.println("Введено неправильное число!");
        } catch (DataAccessException e) {
            output.println("Ошибка чтения! " + e.getMessage());
        }
    }
}

