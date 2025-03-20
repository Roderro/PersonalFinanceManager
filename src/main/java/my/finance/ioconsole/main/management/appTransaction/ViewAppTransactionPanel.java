package my.finance.ioconsole.main.management.appTransaction;

import my.finance.ioconsole.AbstractPanel;
import my.finance.models.AppTransaction;
import my.finance.security.AppSession;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Lazy
public class ViewAppTransactionPanel extends AbstractPanel {
    static final String TEXT = "Посмотреть все транзакции";

    public ViewAppTransactionPanel() {
        super();
    }


    @Override
    public void action() {
        List<AppTransaction> appTransactions = appTransactionRepository.findAllByWallet(appSession.getUser().getWallet());
        output.fPrintAppTransaction(appTransactions);
        waitEnter();
    }
}
