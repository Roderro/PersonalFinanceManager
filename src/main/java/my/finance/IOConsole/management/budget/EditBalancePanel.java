//package my.finance.IOConsole.management.budget;
//
//import my.finance.IOConsole.AbstractPanel;
//import my.finance.models.Wallet;
//import my.finance.security.AppSession;
//import org.hibernate.HibernateException;
//
//import java.util.InputMismatchException;
//
//public class EditBalancePanel extends AbstractPanel {
//    static final String TEXT = "Изменить баланс";
//
//
//    public EditBalancePanel(AppSession appSession) {
//        super(appSession);
//    }
//
//    @Override
//    public void printPanel() {
//
//    }
//
//    @Override
//    public void action() {
//        output.print("Введите новый баланс: ");
//        try {
//            double newBalance = input.nextDouble();
//            Wallet wallet = appSession.getUser().getWallet();
//            wallet.setBalance(newBalance);
//            walletRepository.update(wallet);
//        } catch (HibernateException e) {
//            output.println("Ошибка записи данный, попробуйте снова!");
//        } catch (InputMismatchException e) {
//            output.println("Введено не число");
//        }
//    }
//}
