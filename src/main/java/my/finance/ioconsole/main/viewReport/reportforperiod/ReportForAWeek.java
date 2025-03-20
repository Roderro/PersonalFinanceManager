package my.finance.ioconsole.main.viewReport.reportforperiod;

import my.finance.utils.DatePeriodHelper;
import my.finance.security.AppSession;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class ReportForAWeek extends AbstractReportForPeriodPanel {
    static final String TEXT = "Отчет за текущую неделю";

    public ReportForAWeek() {
        super();
        this.period = DatePeriodHelper.getCurrentWeekPeriod();
    }
}
