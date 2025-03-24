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
public class EditCategoryPanel extends AbstractPanel {
    static final String TEXT = "Редактирование существующей категории";
    private final BudgetCategoryService budgetCategoryService;
    private final ViewCategoryPanel viewCategoryPanel;

    public EditCategoryPanel(BudgetCategoryService budgetCategoryService, ViewCategoryPanel viewCategoryPanel) {
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
                if (newLimit < 0) throw new IllegalArgumentException();
                category.setBudgetLimit(newLimit);
            }
            budgetCategoryService.save(category);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Лимит должен быть положительным!");
        } catch (InputMismatchException e) {
            output.println("Введено не число!");
        } catch (IndexOutOfBoundsException e) {
            output.println("Введено неправильное число!");
        } catch (Exception e) {
            output.println("Ошибка обновления данных! " + e.getMessage());
        }
    }
}
