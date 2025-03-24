package my.finance.ioconsole.main.viewReport;

import my.finance.ioconsole.AbstractPanel;
import my.finance.security.AppSession;
import my.finance.service.appTransaction.AppTransactionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Lazy
@Profile("console")
public class GeneralReportsPanel extends AbstractPanel {
    static final String TEXT = "Общий отчет";

    private final AppTransactionService appTransactionService;

    public GeneralReportsPanel(AppTransactionService appTransactionService) {
        super();
        this.appTransactionService = appTransactionService;
    }


    @Override
    public void action() {
        double reportAllIncome = appTransactionService.sumByTypedCategories(appSession.getUser(), true);
        double reportAllCost = appTransactionService.sumByTypedCategories(appSession.getUser(), false);
        output.printf("""
                Общий доход: %.1f
                Доход по категориям:
                %s%n""", reportAllIncome, fStringReportIncomeCategories());
        output.printLine(35);
        output.printf("""
                Общий расход: %.1f
                Расходы по категориям:
                %s
                """, reportAllCost, fStringReportCostCategories());
        waitEnter();
    }


    private String fStringReportIncomeCategories() {
        List<Object[]> reports = appTransactionService.selectIncomeAndAggregateAmount(appSession.getUser().getWallet());
        String tableOfContents = "%-15s%-20s%n".formatted("Категория", "Доход по категории");
        return tableOfContents + reports.stream()
                .map(report -> String.format("%-15s%-20.1f", report[0], report[1]))
                .collect(Collectors.joining("\n"));
    }

    private String fStringReportCostCategories() {
        List<Object[]> reports = appTransactionService.selectExpensesAndCalRemainder(appSession.getUser().getWallet());
        String tableOfContents = "%-15s%-25s%-16s %n".formatted("Категория", "Расходы по категории", "Оставшийся лимит");
        return tableOfContents + reports.stream()
                .map(report -> String.format("%-15s%-25.1f%.1f", report[0], report[1], report[2]))
                .collect(Collectors.joining("\n"));
    }

}
