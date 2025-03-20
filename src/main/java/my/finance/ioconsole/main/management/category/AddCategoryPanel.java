package my.finance.ioconsole.main.management.category;

import my.finance.ioconsole.AbstractPanel;
import my.finance.models.BudgetCategory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

@Component
@Lazy
public class AddCategoryPanel extends AbstractPanel {
    static final String TEXT = "Добавление категории";


    public AddCategoryPanel() {
        super();
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


     public boolean getTypeCategory() throws RuntimeException {
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

    public BudgetCategory createCategory(boolean isIncome) throws RuntimeException {
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
            budgetCategoryRepository.save(budgetCategory);
            output.println("Категория %s создана!".formatted(budgetCategory.getCategoryName()));
            return budgetCategory;

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Лимит должен быть положительным!");
        } catch (InputMismatchException e) {
            throw new RuntimeException("Введено не число!");
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать категорию!");
        }
    }
}