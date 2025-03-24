package my.finance.ioconsole.main;


import my.finance.ioconsole.AbstractMainPanel;
import my.finance.ioconsole.close.CloseApplicationPanel;
import my.finance.ioconsole.Panel;
import my.finance.ioconsole.authentication.AuthenticationMainPanel;
import my.finance.security.AppSession;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

@Component
@Profile("console")
public class MainPanel extends AbstractMainPanel {
    static final String TEXT = "Главная страница";

    public MainPanel() {
        super();
    }

    @Override
    protected Class<? extends Panel> getClassParentPanel() {
        return this.getClass();
    }

    @Override
    protected void printSpecialPanel() {
        output.printf("%2d: %s \n", children.size() + 1, "Выйти из аккаунта");
        output.printf("%2d: %s \n", children.size() + 2, "Закрыть приложение");
    }

    @Override
    protected Class<? extends Panel> getSpecialPanel(int inputNumber) {
        if (inputNumber == children.size() + 1) {
            appSession.logout();
            return AuthenticationMainPanel.class;
        } else if (inputNumber == children.size() + 2) {
            return CloseApplicationPanel.class;
        } else throw new InputMismatchException();
    }

}


