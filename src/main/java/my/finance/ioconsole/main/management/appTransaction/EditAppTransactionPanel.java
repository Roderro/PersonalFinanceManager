package my.finance.ioconsole.main.management.appTransaction;

import my.finance.ioconsole.AbstractPanel;
import my.finance.models.AppTransaction;
import my.finance.security.AppSession;
import org.hibernate.HibernateException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;

@Component
@Lazy
public class EditAppTransactionPanel extends AbstractPanel {
    static final String TEXT = "Изменить параметры транзакции";

    public EditAppTransactionPanel() {
        super();
    }

    @Override
    public void action() {
        List<AppTransaction> userAppTransaction = appTransactionRepository.findAllByWallet(appSession.getUser().getWallet());
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
            appTransactionRepository.save(appTransaction);
        } catch (InputMismatchException e) {
            output.println("Введено не число!");
        } catch (IndexOutOfBoundsException e) {
            output.println("Введено неправильное число!");
        } catch (HibernateException e) {
            output.println("Ошибка обновления данных!");
        }
    }
}
