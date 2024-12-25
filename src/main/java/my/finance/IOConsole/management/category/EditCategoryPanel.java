package my.finance.IOConsole.management.category;

import my.finance.IOConsole.AbstractPanel;
import my.finance.models.BudgetCategory;
import my.finance.security.AppSession;
import org.hibernate.HibernateException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Stream;

public class EditCategoryPanel extends ViewCategoryPanel {
    static final String TEXT = "Редактирование существующей категории";


    public EditCategoryPanel(AppSession appSession) {
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
        output.print("Введите номер категории: ");
        try {
            int inputNum = input.nextInt();
            BudgetCategory category = userCategories.get(inputNum - 1);
            output.print("Введите новое название категории: ");
            String newNameCategory = input.next();
            category.setCategoryName(newNameCategory);
            if (!category.isIncome()) {
                output.printf("Введите новый лимит для категории '%s': ", newNameCategory);
                double newLimit = input.nextDouble();
                category.setBudgetLimit(newLimit);
            }
            budgetCategoryRepository.update(category);
        } catch (InputMismatchException e) {
            output.println("Введено не число!");
        } catch (IndexOutOfBoundsException e) {
            output.println("Введено неправильное число!");
        }catch (HibernateException e){
            output.println("Ошибка обновления данных!");
        }
    }
}
