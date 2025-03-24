package my.finance.ioconsole.main.viewReport.reportforperiod;

import lombok.Setter;
import my.finance.ioconsole.AbstractPanel;
import my.finance.models.AppTransaction;
import my.finance.service.appTransaction.AppTransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;


public class AbstractReportForPeriodPanel extends AbstractPanel {

    protected LocalDate[] period;
    @Autowired
    @Setter
    private AppTransactionService appTransactionService;

    public AbstractReportForPeriodPanel() {
        super();
    }

    @Override
    public void action() {
        LocalDate startDate = period[0];
        LocalDate endDate = period[1];
        double income = appTransactionService.sumByTypedCategoriesForPeriod(appSession.getWallet(),
                true, startDate, endDate);
        double expenses = appTransactionService.sumByTypedCategoriesForPeriod(appSession.getWallet(),
                false, startDate, endDate);
        List<AppTransaction> appTransactions = appTransactionService
                .getAllForPeriod(appSession.getWallet(), startDate, endDate);
        output.fPrintReportByPeriod(income, expenses, appTransactions, startDate, endDate);
        waitEnter();
    }
}
