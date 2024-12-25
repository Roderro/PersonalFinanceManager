package my.finance.IOConsole.viewReport.reportforperiod;

import my.finance.Utils.DatePeriodHelper;
import my.finance.security.AppSession;

public class ReportForYear extends AbstractReportForPeriodPanel {
    static final String TEXT = "Отчет за год";

    public ReportForYear(AppSession appSession) {
        super(appSession);
        this.period = DatePeriodHelper.getCurrentYearPeriod();
    }

}
