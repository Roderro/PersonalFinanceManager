package my.finance.IOConsole.viewReport.reportforperiod;

import my.finance.IOConsole.AbstractPanel;
import my.finance.Utils.DatePeriodHelper;
import my.finance.models.AppTransaction;
import my.finance.security.AppSession;

import java.time.LocalDateTime;
import java.util.List;

public class ReportForAWeek extends AbstractReportForPeriodPanel {
    static final String TEXT = "Отчет за текущую неделю";

    public ReportForAWeek(AppSession appSession) {
        super(appSession);
        this.period = DatePeriodHelper.getCurrentWeekPeriod();
    }
}
