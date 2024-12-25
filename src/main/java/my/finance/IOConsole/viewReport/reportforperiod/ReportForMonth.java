package my.finance.IOConsole.viewReport.reportforperiod;

import my.finance.IOConsole.AbstractPanel;
import my.finance.Utils.DatePeriodHelper;
import my.finance.security.AppSession;

public class ReportForMonth extends AbstractReportForPeriodPanel {
    static final String TEXT = "Отчет за месяц";

    public ReportForMonth(AppSession appSession) {
        super(appSession);
        this.period = DatePeriodHelper.getCurrentMonthPeriod();
    }

}
