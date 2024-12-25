package my.finance.IOConsole.management.appTransaction;

import my.finance.IOConsole.AbstractPanel;
import my.finance.IOConsole.Panel;
import my.finance.models.AppTransaction;
import my.finance.models.BudgetCategory;
import my.finance.security.AppSession;
import org.hibernate.HibernateException;

import java.util.InputMismatchException;
import java.util.List;

public class EditAppTransactionPanel extends AbstractPanel {
    static final String TEXT = "Изменить параметры транзакции";

    public EditAppTransactionPanel(AppSession appSession) {
        super(appSession);
    }

    @Override
    public void action() {
        List<AppTransaction> userAppTransaction = appTransactionRepository.findAll(appSession.getUser().getWallet());
        output.fPrintAppTransaction(userAppTransaction);
        if (userAppTransaction.isEmpty()) return;
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
            appTransactionRepository.update(appTransaction);
        } catch (InputMismatchException e) {
            output.println("Введено не число!");
        } catch (IndexOutOfBoundsException e) {
            output.println("Введено неправильное число!");
        } catch (HibernateException e) {
            output.println("Ошибка обновления данных!");
        }
    }
}
