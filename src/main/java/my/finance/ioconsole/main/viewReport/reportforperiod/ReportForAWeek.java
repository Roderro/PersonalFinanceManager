package my.finance.ioconsole.main.viewReport.reportforperiod;

import my.finance.utils.DatePeriodHelper;
import my.finance.security.AppSession;

public class ReportForAWeek extends AbstractReportForPeriodPanel {
    static final String TEXT = "Отчет за текущую неделю";

    public ReportForAWeek(AppSession appSession) {
        super(appSession);
        this.period = DatePeriodHelper.getCurrentWeekPeriod();
    }
}
