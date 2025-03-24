package my.finance.ioconsole.main.management.appTransaction;

import my.finance.ioconsole.AbstractPanel;
import my.finance.models.AppTransaction;
import my.finance.service.appTransaction.AppTransactionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Lazy
@Profile("console")
public class ViewAppTransactionPanel extends AbstractPanel {
    static final String TEXT = "Посмотреть все транзакции";
    private final AppTransactionService appTransactionService;

    public ViewAppTransactionPanel(AppTransactionService appTransactionService) {
        super();
        this.appTransactionService = appTransactionService;
    }


    @Override
    public void action() {
        List<AppTransaction> appTransactions = appTransactionService.findAllByWallet(appSession.getWallet());
        output.fPrintAppTransaction(appTransactions);
        waitEnter();
    }
}
