package my.finance.ioconsole.authentication;

import my.finance.ioconsole.AbstractMainPanel;
import my.finance.ioconsole.close.CloseApplicationPanel;
import my.finance.ioconsole.Panel;
import my.finance.security.AppSession;
import my.finance.security.Authentication;
import my.finance.security.StandartAuthentication;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;


@Component
public class AuthenticationMainPanel extends AbstractMainPanel {
    static final String TEXT = "Аутентификация";
    private final Authentication authentication;


    public AuthenticationMainPanel(AppSession appSession,Authentication authentication) {
        super();
        this.authentication = authentication;
    }

    @Override
    protected void printSpecialPanel() {
        output.printf("%2d: %s \n", children.size() + 1, "Закрыть приложение");
    }

    @Override
    protected Class<? extends Panel> getSpecialPanel(int inputNumber) {
        if (inputNumber == children.size() + 1) {
            appSession = null;
            return CloseApplicationPanel.class;
        } else throw new InputMismatchException();
    }

    @Override
    protected Class<? extends Panel> getClassParentPanel() {
        return this.getClass();
    }
}
