package my.finance.IOConsole.viewReport;

import my.finance.IOConsole.AbstractPanel;
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
                Общие расходы: %.1f
                Бюджет по категориям:
                %s
                """, reportAllIncome, fStringReportIncomeCategories(), reportAllCost, fStringReportCostCategories());
        waitEnter();
    }


    private String fStringReportIncomeCategories() {
        List<Object[]> report = appTransactionRepository.selectIncomeAndAggregateAmount(appSession.getUser().getWallet());
        return report.stream()
                .map(category -> String.format(" - %s: %.1f", category[0], category[1]))
                .collect(Collectors.joining("\n"));
    }

    private String fStringReportCostCategories() {
        List<Object[]> report = appTransactionRepository.selectExpensesAndCalRemainder(appSession.getUser().getWallet());
        return report.stream()
                .map(category -> String.format(" - %s:%.1f Оставшийся бюджет:%.1f", category[0], category[1], category[2]))
                .collect(Collectors.joining("\n"));
    }

}
