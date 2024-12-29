package my.finance.ioconsole.main.management.appTransaction;

import my.finance.ioconsole.AbstractMainPanel;
import my.finance.security.AppSession;

public class AppTransactionMainPanel extends AbstractMainPanel {
    static final String TEXT = "Управление транзакциями";


    public AppTransactionMainPanel(AppSession appSession) {
        super(appSession);
    }
}
