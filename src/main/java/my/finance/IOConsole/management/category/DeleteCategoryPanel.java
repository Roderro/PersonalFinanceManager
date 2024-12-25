package my.finance.IOConsole.management.category;

import my.finance.IOConsole.AbstractPanel;
import my.finance.IOConsole.Panel;
import my.finance.models.BudgetCategory;
import my.finance.security.AppSession;
import org.hibernate.HibernateException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Stream;

public class DeleteCategoryPanel extends ViewCategoryPanel {
    static final String TEXT = "Удаление категории";


    public DeleteCategoryPanel(AppSession appSession) {
        super(appSession);
    }


    @Override
    public void action() {
        List<BudgetCategory> incomeCategories = budgetCategoryRepository
                .findAllUserTypedCategories(appSession.getUser(), true);
        List<BudgetCategory> expenseCategories = budgetCategoryRepository
                .findAllUserTypedCategories(appSession.getUser(), false);
        output.fPrintCategories(incomeCategories,expenseCategories);
        List<BudgetCategory> userCategories = Stream
                .concat(incomeCategories.stream(), expenseCategories.stream())
                .toList();
        if (userCategories.isEmpty()) return;
        output.print("Введите номер удаляемой категории: ");
        try {
            int inputNum = input.nextInt();
            BudgetCategory delCategory = userCategories.get(inputNum - 1);
            budgetCategoryRepository.delete(delCategory);
            output.println(STR."Категория \{delCategory.getCategoryName()} удалена");
        } catch (InputMismatchException e) {
            output.println("Введено не число!");
        } catch (IndexOutOfBoundsException e) {
            output.println("Введено неправильное число!");
        } catch (HibernateException e) {
            output.println("Ошибка удаления!");
        }
    }
}
