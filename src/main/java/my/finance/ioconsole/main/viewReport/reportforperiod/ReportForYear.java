package my.finance.ioconsole.main.viewReport.reportforperiod;

import my.finance.utils.DatePeriodHelper;
import my.finance.security.AppSession;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Profile("console")
public class ReportForYear extends AbstractReportForPeriodPanel {
    static final String TEXT = "Отчет за год";

    public ReportForYear() {
        super();
        this.period = DatePeriodHelper.getCurrentYearPeriod();
    }

}
