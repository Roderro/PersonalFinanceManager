package my.finance.ioconsole.main.management;

import my.finance.ioconsole.AbstractMainPanel;
import my.finance.security.AppSession;

public class ManagementMainPanel extends AbstractMainPanel {
    static final String TEXT = "Управление финансами";


    public ManagementMainPanel(AppSession appSession) {
        super(appSession);
    }

}
