package my.finance.IOConsole.management.appTransaction;

import my.finance.IOConsole.AbstractMainPanel;
import my.finance.security.AppSession;

public class AppTransactionMainPanel extends AbstractMainPanel {
    static final String TEXT = "Управление транзакциями";


    public AppTransactionMainPanel(AppSession appSession) {
        super(appSession);
    }
}
