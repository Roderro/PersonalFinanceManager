package my.finance.ioconsole.main.viewReport.reportforperiod;

import my.finance.utils.DatePeriodHelper;
import my.finance.security.AppSession;

public class ReportForMonth extends AbstractReportForPeriodPanel {
    static final String TEXT = "Отчет за месяц";

    public ReportForMonth(AppSession appSession) {
        super(appSession);
        this.period = DatePeriodHelper.getCurrentMonthPeriod();
    }

}
