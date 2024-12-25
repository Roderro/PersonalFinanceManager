package my.finance.IOConsole.management.category;

import my.finance.IOConsole.AbstractPanel;
import my.finance.IOConsole.Panel;
import my.finance.models.BudgetCategory;
import my.finance.security.AppSession;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ViewCategoryPanel extends AbstractPanel {
    static final String TEXT = "Просмотр списка категорий";


    protected List<BudgetCategory> userCategories;

    public ViewCategoryPanel(AppSession appSession) {
        super(appSession);

    }


    @Override
    public void action() {
        List<BudgetCategory> incomeCategories = budgetCategoryRepository
                .findAllUserTypedCategories(appSession.getUser(), true);
        List<BudgetCategory> expenseCategories = budgetCategoryRepository
                .findAllUserTypedCategories(appSession.getUser(), false);
        output.fPrintCategories(incomeCategories,expenseCategories);
        waitEnter();
    }


}
