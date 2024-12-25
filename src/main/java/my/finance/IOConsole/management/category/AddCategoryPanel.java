package my.finance.IOConsole.management.category;

import jakarta.persistence.PersistenceException;
import my.finance.IOConsole.AbstractPanel;
import my.finance.IOConsole.Panel;
import my.finance.models.BudgetCategory;
import my.finance.repository.BudgetCategoryRepository;
import my.finance.security.AppSession;
import org.hibernate.HibernateException;

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
            BudgetCategory category = createCategory(isIncome);
            output.println(STR."Категория \{category.getCategoryName()} создана!");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());

        }
    }


    protected boolean getTypeCategory() throws RuntimeException {
        output.print("""
                Выберите тип:
                1. Доход
                2. Расход
                \s
                Введите число:\s""");
        int inputInt;
        try {
            inputInt = input.nextInt();
            if (inputInt != 1 && inputInt != 2) throw new InputMismatchException();
        } catch (InputMismatchException e) {
            throw new RuntimeException("Введено не верное число!");
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
                if (limit < 0) throw new InputMismatchException();
                budgetCategory = new BudgetCategory(appSession.getUser().getWallet(), nameCategory, limit);
            }
            budgetCategoryRepository.add(budgetCategory);
            return budgetCategory;
        }
        catch (PersistenceException e){
            throw new RuntimeException("Категория не создана,название должно быть уникальным!");
        }
        catch (Exception e) {
            throw new RuntimeException("Не удалось создать категорию!");
        }
    }
}