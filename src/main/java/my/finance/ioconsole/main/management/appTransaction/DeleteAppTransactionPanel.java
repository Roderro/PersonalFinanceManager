package my.finance.ioconsole.main.management.appTransaction;

import my.finance.ioconsole.AbstractPanel;
import my.finance.models.AppTransaction;
import my.finance.security.AppSession;
import org.hibernate.HibernateException;

import java.util.InputMismatchException;
import java.util.List;

public class DeleteAppTransactionPanel extends AbstractPanel {
    static final String TEXT = "Удаление транзакции";


    public DeleteAppTransactionPanel(AppSession appSession) {
        super(appSession);
    }


    @Override
    public void action() {
        List<AppTransaction> userAppTransaction = appTransactionRepository.findAll(appSession.getUser().getWallet());
        output.fPrintAppTransaction(userAppTransaction);
        if (userAppTransaction.isEmpty()) {
            waitEnter();
            return;
        }
        output.print("Введите номер удаляемой транзакции: ");
        try {
            int inputNum = input.nextInt();
            AppTransaction delAppTransaction = userAppTransaction.get(inputNum - 1);
            appTransactionRepository.delete(delAppTransaction);
            output.println(STR."Транзакция \{delAppTransaction.getDescription()} удалена");
        } catch (InputMismatchException e) {
            output.println("Введено не число!");
        } catch (IndexOutOfBoundsException e) {
            output.println("Введено неправильное число!");
        } catch (HibernateException e) {
            output.println("Ошибка удаления!");
        }
    }

}
