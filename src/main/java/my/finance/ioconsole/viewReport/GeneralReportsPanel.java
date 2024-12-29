package my.finance.ioconsole.viewReport;

import my.finance.ioconsole.AbstractPanel;
import my.finance.security.AppSession;

import java.util.List;
import java.util.stream.Collectors;

public class GeneralReportsPanel extends AbstractPanel {
    static final String TEXT = "Общий отчет";

    public GeneralReportsPanel(AppSession appSession) {
        super(appSession);
    }


    @Override
    public void action() {
        double reportAllIncome = appTransactionRepository.sumByTypedCategories(appSession.getUser(), true);
        double reportAllCost = appTransactionRepository.sumByTypedCategories(appSession.getUser(), false);
        output.printf("""
                Общий доход: %.1f
                Доход по категориям:
                %s
                Общий расход: %.1f
                Расходы по категориям:
                %s
                """, reportAllIncome, fStringReportIncomeCategories(), reportAllCost, fStringReportCostCategories());
        waitEnter();
    }


    private String fStringReportIncomeCategories() {
        List<Object[]> reports = appTransactionRepository.selectIncomeAndAggregateAmount(appSession.getUser().getWallet());
        String tableOfContents = "%-15s%-20s%n".formatted("Категория", "Доход по категории");
        return tableOfContents+reports.stream()
                .map(report -> String.format("%-15s%-20.1f", report[0], report[1]))
                .collect(Collectors.joining("\n"));
    }

    private String fStringReportCostCategories() {
        List<Object[]> reports = appTransactionRepository.selectExpensesAndCalRemainder(appSession.getUser().getWallet());
        String tableOfContents = "%-15s%-25s%-16s %n".formatted("Категория", "Расходы по категории", "Оставшийся лимит");
        return tableOfContents + reports.stream()
                .map(report -> String.format("%-15s%-25.1f%.1f", report[0], report[1], report[2]))
                .collect(Collectors.joining("\n"));
    }

}
