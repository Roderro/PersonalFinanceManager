package my.finance.ioconsole.main.management.category;

import my.finance.ioconsole.AbstractPanel;
import my.finance.models.BudgetCategory;
import my.finance.service.budgetCategory.BudgetCategoryService;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;

@Component
@Lazy
@Profile("console")
public class DeleteCategoryPanel extends AbstractPanel {
    static final String TEXT = "Удаление категории";

    private final BudgetCategoryService budgetCategoryService;
    private final ViewCategoryPanel viewCategoryPanel;

    public DeleteCategoryPanel(BudgetCategoryService budgetCategoryService, ViewCategoryPanel viewCategoryPanel) {
        super();
        this.budgetCategoryService = budgetCategoryService;
        this.viewCategoryPanel = viewCategoryPanel;
    }


    @Override
    public void action() {
        List<BudgetCategory> userCategories = viewCategoryPanel.fPrintCategoriesAndReturnConcatCategory();
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
                budgetCategoryService.delete(delCategory);
                output.println("Категория %s удалена".formatted(delCategory.getCategoryName()));
            } else return;
        } catch (InputMismatchException e) {
            output.println("Введено не число!");
        } catch (IndexOutOfBoundsException e) {
            output.println("Введено неправильное число!");
        } catch (Exception e) {
            output.println("Ошибка удаления! " + e.getMessage());
        }
    }
}
