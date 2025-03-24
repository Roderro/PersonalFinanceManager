package my.finance.ioconsole.main.viewReport.reportforperiod;

import my.finance.ioconsole.AbstractMainPanel;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Profile("console")
public class ReportsForPeriodMainPanel extends AbstractMainPanel {
    static final String TEXT = "Отчет за период";

    public ReportsForPeriodMainPanel() {
        super();
    }

}
