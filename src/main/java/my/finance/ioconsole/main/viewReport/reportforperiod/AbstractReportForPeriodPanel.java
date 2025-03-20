package my.finance.ioconsole.main.viewReport.reportforperiod;

import my.finance.ioconsole.AbstractPanel;
import my.finance.models.AppTransaction;
import my.finance.security.AppSession;

import java.time.LocalDate;
import java.util.List;


public class AbstractReportForPeriodPanel extends AbstractPanel {

    protected LocalDate[] period;

    public AbstractReportForPeriodPanel() {
        super();
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
