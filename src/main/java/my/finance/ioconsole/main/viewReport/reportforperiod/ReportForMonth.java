package my.finance.ioconsole.main.viewReport.reportforperiod;

import my.finance.utils.DatePeriodHelper;
import my.finance.security.AppSession;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class ReportForMonth extends AbstractReportForPeriodPanel {
    static final String TEXT = "Отчет за месяц";

    public ReportForMonth() {
        super();
        this.period = DatePeriodHelper.getCurrentMonthPeriod();
    }

}
