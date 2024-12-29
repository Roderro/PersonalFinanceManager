package my.finance.ioconsole.management.appTransaction;

import my.finance.ioconsole.AbstractPanel;
import my.finance.models.AppTransaction;
import my.finance.security.AppSession;

import java.util.List;

public class ViewAppTransactionPanel extends AbstractPanel {
    static final String TEXT = "Посмотреть все транзакции";

    public ViewAppTransactionPanel(AppSession appSession) {
        super(appSession);
    }


    @Override
    public void action() {
        List<AppTransaction> appTransactions = appTransactionRepository.findAll(appSession.getUser().getWallet());
        output.fPrintAppTransaction(appTransactions);
        waitEnter();
    }
}
