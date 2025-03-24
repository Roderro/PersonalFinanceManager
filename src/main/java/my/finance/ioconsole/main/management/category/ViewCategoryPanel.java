package my.finance.ioconsole.main.management.category;

import my.finance.ioconsole.AbstractPanel;
import my.finance.models.BudgetCategory;
import my.finance.service.budgetCategory.BudgetCategoryService;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@Lazy
@Profile("console")
public class ViewCategoryPanel extends AbstractPanel {
    static final String TEXT = "Просмотр списка категорий";

    private final BudgetCategoryService budgetCategoryService;

    public ViewCategoryPanel(BudgetCategoryService budgetCategoryService) {
        super();
        this.budgetCategoryService = budgetCategoryService;
    }


    @Override
    public void action() {
        fPrintCategoriesAndReturnConcatCategory();
        waitEnter();
    }

    public List<BudgetCategory> fPrintCategoriesAndReturnConcatCategory() {
        List<BudgetCategory> incomeCategories = budgetCategoryService
                .findAllUserTypedCategories(true);
        List<BudgetCategory> expenseCategories = budgetCategoryService
                .findAllUserTypedCategories(false);
        output.fPrintCategories(incomeCategories, expenseCategories);
        return Stream
                .concat(incomeCategories.stream(), expenseCategories.stream())
                .toList();
    }
}
