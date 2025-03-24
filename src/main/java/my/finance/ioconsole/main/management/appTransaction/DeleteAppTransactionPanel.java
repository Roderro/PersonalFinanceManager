package my.finance.ioconsole.main.management.appTransaction;

import my.finance.ioconsole.AbstractPanel;
import my.finance.models.AppTransaction;
import my.finance.security.AppSession;
import my.finance.service.appTransaction.AppTransactionService;
import org.hibernate.HibernateException;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;

@Component
@Lazy
@Profile("console")
public class DeleteAppTransactionPanel extends AbstractPanel {
    static final String TEXT = "Удаление транзакции";

    private final AppTransactionService appTransactionService;

    public DeleteAppTransactionPanel(AppTransactionService appTransactionService) {
        super();
        this.appTransactionService = appTransactionService;
    }


    @Override
    public void action() {
        List<AppTransaction> userAppTransaction = appTransactionService
                .findAllByWallet(appSession.getWallet());
        output.fPrintAppTransaction(userAppTransaction);
        if (userAppTransaction.isEmpty()) {
            waitEnter();
            return;
        }
        output.print("Введите номер удаляемой транзакции: ");
        try {
            int inputNum = input.nextInt();
            AppTransaction delAppTransaction = userAppTransaction.get(inputNum - 1);
            appTransactionService.delete(delAppTransaction);
            output.println("Транзакция %s удалена".formatted(delAppTransaction.getDescription()));
        } catch (InputMismatchException e) {
            output.println("Введено не число!");
        } catch (IndexOutOfBoundsException e) {
            output.println("Введено неправильное число!");
        } catch (DataAccessException e) {
            output.println(e.getMessage());
        }
    }

}
