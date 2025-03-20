package my.finance.ioconsole.main.viewReport.reportforperiod;

import my.finance.ioconsole.AbstractMainPanel;
import my.finance.security.AppSession;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class ReportsForPeriodMainPanel extends AbstractMainPanel {
    static final String TEXT = "Отчет за период";

    public ReportsForPeriodMainPanel() {
        super();
    }

}
