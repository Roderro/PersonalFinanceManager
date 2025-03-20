package my.finance.ioconsole.main.management.category;

import my.finance.ioconsole.AbstractMainPanel;
import my.finance.ioconsole.AbstractPanel;
import my.finance.models.BudgetCategory;
import org.hibernate.HibernateException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Stream;

@Component
@Lazy
public class DeleteCategoryPanel extends AbstractPanel {
    static final String TEXT = "Удаление категории";


    public DeleteCategoryPanel() {
        super();
    }


    @Override
    public void action() {
        List<BudgetCategory> incomeCategories = budgetCategoryRepository
                .findAllUserTypedCategories(appSession.getUser(), true);
        List<BudgetCategory> expenseCategories = budgetCategoryRepository
                .findAllUserTypedCategories(appSession.getUser(), false);
        output.fPrintCategories(incomeCategories, expenseCategories);
        List<BudgetCategory> userCategories = Stream
                .concat(incomeCategories.stream(), expenseCategories.stream())
                .toList();
        if (userCategories.isEmpty()) {
            waitEnter();
            return;
        }
        output.print("Введите номер удаляемой категории: ");
        try {
            int inputNum = input.nextInt();
            BudgetCategory delCategory = userCategories.get(inputNum - 1);
            output.print("При удалении категории удалятся все связанные с ней транзакции, продолжить удаление(д/н):");
            String decision = input.next();
            if (decision.equals("д") || decision.equals("да") || decision.equals("y") || decision.equals("yes")) {
                budgetCategoryRepository.delete(delCategory);
                output.println("Категория %s удалена".formatted(delCategory.getCategoryName()));
            } else return;
        } catch (InputMismatchException e) {
            output.println("Введено не число!");
        } catch (IndexOutOfBoundsException e) {
            output.println("Введено неправильное число!");
        } catch (HibernateException e) {
            output.println("Ошибка удаления!");
        }
    }
}
