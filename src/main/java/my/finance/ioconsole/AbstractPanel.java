package my.finance.ioconsole;

import jakarta.annotation.PostConstruct;
import lombok.Setter;
import my.finance.repository.*;
import my.finance.security.AppSession;
import my.finance.transport.Input;

import my.finance.transport.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


/**
 * Абстрактный базовый класс для определения пользовательских панелей в приложении.
 * Предоставляет общие методы и свойства для обработки взаимодействия с пользователем.
 */
@Setter
public abstract class AbstractPanel implements Panel {
    //Для корректного создания дочерних панелей в AbstractMainPanel,
    // при создании AbstractPanel необходимо переопределить TEXT
    private static final String TEXT = "Some panel text";
    @Autowired
    protected AppSession appSession;
    protected Class<? extends Panel> parentClass;
    protected Class<? extends Panel> nextPanelClass;
    @Autowired
    protected Input input;
    @Autowired
    protected Output output;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected WalletRepository walletRepository;
    @Autowired
    protected BudgetCategoryRepository budgetCategoryRepository;
    @Autowired
    protected AppTransactionRepository appTransactionRepository;
    @Autowired
    private ApplicationContext applicationContext;


//    public AbstractPanel(AppSession appSession, Input input, Output output, UserRepository userRepository, WalletRepository walletRepository, BudgetCategoryRepository budgetCategoryRepository, AppTransactionRepository appTransactionRepository) {
//        this.appSession = appSession;
//        this.input = input;
//        this.output = output;
//        this.userRepository = userRepository;
//        this.walletRepository = walletRepository;
//        this.budgetCategoryRepository = budgetCategoryRepository;
//        this.appTransactionRepository = appTransactionRepository;
//        this.parentClass = this.nextPanelClass = getClassParentPanel();
//    }

    @PostConstruct
    public void init() {
        this.parentClass = this.nextPanelClass = getClassParentPanel();
    }

    public void printLoginAndBalance() {
        String info = "Не авторизован";
        if (checkAuthorizedUser()) {
            String login = appSession.getUser().getLogin();
            double balance = walletRepository.calculateBalance(appSession.getWallet().getId());
            appSession.getWallet().setBalance(balance);
            walletRepository.save(appSession.getWallet());
            info = String.format("Логин: %s Баланс: %.2f", login, balance);
        }
        output.printLine(info.length());
        output.println(info);
        output.printLine(info.length());
    }

    public void printPanel() {
    }

    public abstract void action();


    public final boolean checkAuthorizedUser() {
        return appSession.getUser() != null;
    }

    public final Panel nextPanel() {
        Class<? extends Panel> nextClass = nextPanelClass;
        return applicationContext.getBean(nextClass);
    }


    protected Class<? extends Panel> getClassParentPanel() {
        Class<? extends Panel> targetClass = null;
        try {
            Class<?> currentClass = this.getClass();
            String currentPackageName = currentClass.getPackage().getName();
            String parentPackageName = getParentPackageName(currentPackageName);
            List<Class<?>> classes = getClassesInCurrentPackage(parentPackageName, true);
            targetClass = (Class<? extends Panel>) classes.get(0);
        } catch (ClassNotFoundException | IOException e) {
            output.error("Класс не найден: " + e.getMessage());
        }
        return targetClass;
    }

    private final String getParentPackageName(String currentPackageName) {
        if (this instanceof AbstractMainPanel) {
            int lastIndexPoint = -1;
            int countPoint = 0;
            for (int i = 0; i < currentPackageName.length(); i++) {
                if (currentPackageName.charAt(i) == '.') {
                    countPoint++;
                    lastIndexPoint = i;
                }
            }
            if (countPoint > 2) {
                return currentPackageName.substring(0, lastIndexPoint);
            }
        }
        return currentPackageName;
    }

    // Метод для получения списка классов только в текущем пакете (без подпакетов)
    protected List<Class<?>> getClassesInCurrentPackage(String packageName, boolean needMain) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');

        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            if (needMain) {
                classes.addAll(findMainClassesInCurrentDirectory(directory, packageName));
            } else classes.addAll(findClassesInCurrentDirectory(directory, packageName));
        }
        return classes;
    }

    // Метод для поиска классов только в текущей директории (без поддиректорий)
    private List<Class<?>> findClassesInCurrentDirectory(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files == null) return classes;
        for (File file : files) {
            if (file.isFile() && !file.getName().startsWith("_")
                    && !file.getName().startsWith("Abstract") && file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    private List<Class<?>> findMainClassesInCurrentDirectory(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files == null) return classes;
        for (File file : files) {
            if (file.isFile() && !file.getName().startsWith("Abstract")
                    && file.getName().endsWith("MainPanel.class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public void waitEnter() {
        output.print("Нажмите Enter, чтобы продолжить> ");
        input.nextLine();
    }

}