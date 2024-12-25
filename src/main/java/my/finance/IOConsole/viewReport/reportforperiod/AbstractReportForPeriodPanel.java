package my.finance.IOConsole.viewReport.reportforperiod;

import my.finance.IOConsole.AbstractPanel;
import my.finance.Utils.DatePeriodHelper;
import my.finance.models.AppTransaction;
import my.finance.security.AppSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AbstractReportForPeriodPanel extends AbstractPanel {

    protected LocalDate[] period;

    public AbstractReportForPeriodPanel(AppSession appSession) {
        super(appSession);

    }

    @Override
    public void action() {
        LocalDate startDate = period[0];
        LocalDate endDate = period[1];
        double income = appTransactionRepository.sumByTypedCategoriesForPeriod(appSession.getWallet(),
                true, startDate, endDate);
        double expenses = appTransactionRepository.sumByTypedCategoriesForPeriod(appSession.getWallet(),
                false, startDate, endDate);
        List<AppTransaction> appTransactions = appTransactionRepository.getAllForPeriod(appSession.getWallet(), startDate, endDate);
        output.fPrintReportByPeriod(income, expenses, appTransactions, startDate, endDate);
        waitEnter();
    }
}
