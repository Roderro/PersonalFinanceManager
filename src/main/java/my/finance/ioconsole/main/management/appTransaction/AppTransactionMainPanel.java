package my.finance.ioconsole.main.management.appTransaction;

import my.finance.ioconsole.AbstractMainPanel;
import my.finance.security.AppSession;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class AppTransactionMainPanel extends AbstractMainPanel {
    static final String TEXT = "Управление транзакциями";


    public AppTransactionMainPanel() {
        super();
    }
}
