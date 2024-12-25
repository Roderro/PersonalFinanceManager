package my.finance;


import my.finance.IOConsole.MainPanel;
import my.finance.IOConsole.Panel;
import my.finance.IOConsole._authentication.AuthenticationMainPanel;
import my.finance.IOConsole.PanelManager;
import my.finance.models.BudgetCategory;
import my.finance.models.AppTransaction;
import my.finance.models.User;
import my.finance.models.Wallet;
import my.finance.repository.AppTransactionRepository;
import my.finance.repository.BudgetCategoryRepository;
import my.finance.repository.UserRepository;
import my.finance.repository.WalletRepository;
import my.finance.security.AppSession;
import my.finance.security.Authentication;
import my.finance.security.StandartAuthentication;
import org.hibernate.HibernateException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    public static void main(String[] args) throws SQLException {
        //App.initDB();
        boolean genTestData = false;
        AppSession appSession = null;
        Panel startPanel;
        if (genTestData) {
            appSession = initTestData();
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
        String newLogin = "Roder";
        String newPassword = "322";
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


    public static void initDB() {
        User user = new User();
        user.setLogin("Petr");
        User user1 = new User();
        user1.setLogin("Igor");
        User user2 = new User();
        user2.setLogin("Nikita");
        User user3 = new User();
        user3.setLogin("Tigran");
        Wallet walletPetr = new Wallet(0., user);
        Wallet walletIgor = new Wallet(0., user1);
        Wallet walletNikita = new Wallet(0., user2);
        Wallet walletTigran = new Wallet(0., user3);
        user.setWallet(walletPetr);
        user1.setWallet(walletIgor);
        user2.setWallet(walletNikita);
        user3.setWallet(walletTigran);
        BudgetCategory workCategory = new BudgetCategory(user.getWallet(), "Работа");
        user.getWallet().addBudgetCategory(workCategory);
        AppTransaction appTransaction1 = new AppTransaction(user.getWallet(), 6000, workCategory, "work");
        AppTransaction appTransaction2 = new AppTransaction(user.getWallet(), 3000, workCategory, "work");
        BudgetCategory food = new BudgetCategory(user.getWallet(), "Food", 5000);
        user.getWallet().addBudgetCategory(food);
        AppTransaction appTransaction3 = new AppTransaction(user.getWallet(), 600, food, "food");
        user.getWallet().addTransaction(appTransaction1);
        user.getWallet().addTransaction(appTransaction2);
        user.getWallet().addTransaction(appTransaction3);
        List<User> users = new ArrayList<>(Arrays.asList(user, user1, user2, user3));

        UserRepository userRepository = new UserRepository();
        AppTransactionRepository appTransactionRepository = new AppTransactionRepository();
        for (User u : users) {
            userRepository.add(u);
        }
//        List<AppTransaction> userTrans = appTransactionRepository.findAllUserTransaction(user);
//        for (AppTransaction t : userTrans) {
//            System.out.println(t);
        }
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            AppTransaction appTransaction = session.beginTransaction();
//        }


//
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//        session.save(user); // Сохранение записи в БД
//        session.save(user1);
//        session.save(user2);
//        session.save(user3);
//        session.save(walletPetr);
//        session.save(walletIgor);
//        session.save(walletNikita);
//        session.save(walletTigran);
//        transaction.commit();
//        session.close();
    }


