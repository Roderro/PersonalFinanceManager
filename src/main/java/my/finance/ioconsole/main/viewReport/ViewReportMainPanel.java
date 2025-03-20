package my.finance.ioconsole.main.viewReport;

import my.finance.ioconsole.AbstractMainPanel;
import my.finance.security.AppSession;
import org.springframework.stereotype.Component;

@Component
public class ViewReportMainPanel extends AbstractMainPanel {
    public static String TEXT = "Просмотр отчетов";

    public ViewReportMainPanel() {
        super();
    }
}
