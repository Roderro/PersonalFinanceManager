package my.finance.IOConsole;


import my.finance.IOConsole._authentication.AuthenticationMainPanel;
import my.finance.security.AppSession;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;

public class MainPanel extends AbstractMainPanel {
    static final String TEXT = "Главная страница";

    public MainPanel(AppSession appSession) {
        super(appSession);
    }


    @Override
    protected void printSpecialPanel() {
        output.printf("%2d: %s \n", children.size() + 1, "Выйти из аккаунта");
        output.printf("%2d: %s \n", children.size() + 2, "Закрыть приложение");
    }

    @Override
    protected Class<? extends Panel> getSpecialPanel(int inputNumber) {
        if (inputNumber == children.size() + 1) {
            appSession = null;
            return AuthenticationMainPanel.class;
        } else if (inputNumber == children.size() + 2) {
            return CloseApplicationPanel.class;
        } else throw new InputMismatchException();
    }

    @Override
    protected void populateChildren() throws IOException, ClassNotFoundException {
        Class<?> currentClass = this.getClass();
        String packageName = currentClass.getPackage().getName();
        List<String> subPackages = getSubPackages(packageName);
        if (!subPackages.isEmpty()) {
            for (String subPackage :subPackages) {
                children.addAll(getClassesInCurrentPackage(subPackage, true));
            }
        }
    }
}


