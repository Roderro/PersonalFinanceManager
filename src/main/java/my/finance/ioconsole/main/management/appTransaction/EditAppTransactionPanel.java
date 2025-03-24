package my.finance.ioconsole.main.management.appTransaction;

import my.finance.ioconsole.AbstractPanel;
import my.finance.models.AppTransaction;
import my.finance.repository.AppTransactionRepository;
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
public class EditAppTransactionPanel extends AbstractPanel {
    static final String TEXT = "Изменить параметры транзакции";
    private final AppTransactionService appTransactionService;

    public EditAppTransactionPanel(AppTransactionService appTransactionService) {
        super();
        this.appTransactionService = appTransactionService;
    }

    @Override
    public void action() {
        List<AppTransaction> userAppTransaction = appTransactionService.findAllByWallet(appSession.getWallet());
        output.fPrintAppTransaction(userAppTransaction);
        if (userAppTransaction.isEmpty()) {
            waitEnter();
            return;
        }
        output.print("Введите номер транзакции: ");
        try {
            int inputNum = input.nextInt();
            AppTransaction appTransaction = userAppTransaction.get(inputNum - 1);
            output.print("Введите новую сумму транзакции: ");
            double newAmount = input.nextDouble();
            appTransaction.setAmount(newAmount);
            output.print("Введите новое описание: ");
            String newDescription = input.nextLine();
            appTransaction.setDescription(newDescription);
            appTransactionService.save(appTransaction);
        } catch (InputMismatchException e) {
            output.println("Введено не число!");
        } catch (IndexOutOfBoundsException e) {
            output.println("Введено неправильное число!");
        } catch (DataAccessException e) {
            output.println(e.getMessage());
        }
    }
}
