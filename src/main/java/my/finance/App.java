package my.finance;

import my.finance.ioconsole.main.MainPanel;
import my.finance.ioconsole.Panel;
import my.finance.ioconsole.authentication.AuthenticationMainPanel;
import my.finance.ioconsole.PanelManager;
import my.finance.models.BudgetCategory;
import my.finance.models.AppTransaction;
import my.finance.models.User;
import my.finance.repository.AppTransactionRepository;
import my.finance.repository.BudgetCategoryRepository;
import my.finance.repository.UserRepository;
import my.finance.repository.WalletRepository;
import my.finance.security.AppSession;
import my.finance.security.Authentication;
import my.finance.security.StandartAuthentication;
import org.hibernate.HibernateException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;


public class App {

    public static void main(String[] args) {
        boolean genTestData = false;
        Panel startPanel;
        if (genTestData) {
            AppSession appSession = initTestData();
            startPanel = new MainPanel(appSession);
        } else {
            startPanel = new AuthenticationMainPanel();
        }
        PanelManager panelManager = new PanelManager(startPanel);
        panelManager.manage();
    }

    public static AppSession initTestData() {

        UserRepository userRepository = new UserRepository();
        WalletRepository walletRepository = new WalletRepository();
        BudgetCategoryRepository budgetCategoryRepository = new BudgetCategoryRepository();
        AppTransactionRepository appTransactionRepository = new AppTransactionRepository();
        String newLogin = "Test";
        String newPassword = "1234";
        Authentication authentication = new StandartAuthentication(userRepository);
        String hashNewPassword = authentication.getHashFunc().hash(newPassword);
        User newUser = new User(newLogin, hashNewPassword);

        AppSession appSession = null;
        try {
            userRepository.add(newUser);
            appSession = new AppSession(newUser);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        BudgetCategory work = new BudgetCategory(newUser.getWallet(), "Работа");
        BudgetCategory some = new BudgetCategory(newUser.getWallet(), "Подработка");
        BudgetCategory food = new BudgetCategory(newUser.getWallet(), "еда", 15000);
        BudgetCategory game = new BudgetCategory(newUser.getWallet(), "Развлечения", 5000);
        AppTransaction ovanz = new AppTransaction(newUser.getWallet(), 40000, work, "Аванс декабрь");
        AppTransaction zarplata = new AppTransaction(newUser.getWallet(), 50000, work, "ЗП декабрь");
        AppTransaction sushi = new AppTransaction(newUser.getWallet(), 1500, food, "Заказали суши");
        try {
            budgetCategoryRepository.add(work);
            budgetCategoryRepository.add(some);
            budgetCategoryRepository.add(food);
            budgetCategoryRepository.add(game);
            appTransactionRepository.add(ovanz);
            appTransactionRepository.add(zarplata);
            appTransactionRepository.add(sushi);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return appSession;
    }
}


