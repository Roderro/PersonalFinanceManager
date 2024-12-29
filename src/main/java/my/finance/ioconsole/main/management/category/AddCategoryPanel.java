package my.finance.ioconsole.main.management.category;

import jakarta.persistence.PersistenceException;
import my.finance.ioconsole.AbstractPanel;
import my.finance.models.BudgetCategory;
import my.finance.security.AppSession;

import java.util.InputMismatchException;

public class AddCategoryPanel extends AbstractPanel {
    static final String TEXT = "Добавление категории";


    public AddCategoryPanel(AppSession appSession) {
        super(appSession);
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


    protected boolean getTypeCategory() throws RuntimeException {
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

    protected BudgetCategory createCategory(boolean isIncome) throws RuntimeException {
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
            budgetCategoryRepository.add(budgetCategory);
            output.println(STR."Категория \{budgetCategory.getCategoryName()} создана!");
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