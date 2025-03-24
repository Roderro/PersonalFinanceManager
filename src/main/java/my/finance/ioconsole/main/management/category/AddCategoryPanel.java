package my.finance.ioconsole.main.management.category;

import my.finance.exception.budgetCategory.BudgetCategoryAlreadyExistsException;
import my.finance.ioconsole.AbstractPanel;
import my.finance.models.BudgetCategory;
import my.finance.service.budgetCategory.BudgetCategoryService;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

@Component
@Lazy
@Profile("console")
public class AddCategoryPanel extends AbstractPanel {
    static final String TEXT = "Добавление категории";

    private final BudgetCategoryService budgetCategoryService;

    public AddCategoryPanel(BudgetCategoryService budgetCategoryService) {
        super();
        this.budgetCategoryService = budgetCategoryService;
    }


    @Override
    public void action() {
        try {
            boolean isIncome = getTypeCategory();
            createCategory(isIncome);
        } catch (RuntimeException e) {
            output.println(e.getMessage());
        }
    }


    public boolean getTypeCategory() {
        output.print("""
                Выберите тип:
                1. Доход
                2. Расход
                Введите число:\s""");
        int inputInt;
        try {
            inputInt = input.nextInt();
            if (inputInt != 1 && inputInt != 2) throw new InputMismatchException();
        } catch (InputMismatchException e) {
            throw new RuntimeException("Не удалось выбрать тип транзакции!");
        }
        return inputInt != 2;
    }

    public BudgetCategory createCategory(boolean isIncome) {
        String type = isIncome ? "дохода" : "расхода";
        output.printf("Введите название категории %s: ", type);
        String nameCategory = input.next();
        BudgetCategory budgetCategory;
        try {
            if (isIncome) {
                budgetCategory = new BudgetCategory(appSession.getUser().getWallet(), nameCategory);
            } else {
                output.printf("Введите лимит для категории '%s': ", nameCategory);
                double limit = input.nextDouble();
                if (limit < 0) throw new IllegalArgumentException();
                budgetCategory = new BudgetCategory(appSession.getUser().getWallet(), nameCategory, limit);
            }
            budgetCategoryService.save(budgetCategory);
            output.println("Категория %s создана!".formatted(budgetCategory.getCategoryName()));
            return budgetCategory;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Лимит должен быть положительным!");
        } catch (InputMismatchException e) {
            throw new RuntimeException("Введено не число!");
        } catch (BudgetCategoryAlreadyExistsException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать категорию! " + e.getMessage());
        }
    }
}