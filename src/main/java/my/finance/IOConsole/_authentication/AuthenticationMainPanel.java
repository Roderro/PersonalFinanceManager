package my.finance.IOConsole._authentication;

import my.finance.IOConsole.AbstractMainPanel;
import my.finance.IOConsole.CloseApplicationPanel;
import my.finance.IOConsole.Panel;
import my.finance.security.AppSession;
import my.finance.security.Authentication;
import my.finance.security.StandartAuthentication;

import java.util.InputMismatchException;


/**
 * Панель аутентификации пользователя в приложении.
 * Позволяет пользователям создать новую сессию, войти в существующую сессию или выйти из приложения.
 */
public class AuthenticationMainPanel extends AbstractMainPanel {
    static final String TEXT = "Выйти из аккаунта";

    private final Authentication authentication;

    public AuthenticationMainPanel() {
        this(null);
    }


    public AuthenticationMainPanel(AppSession appSession) {
        super(appSession);
        this.authentication = new StandartAuthentication(userRepository);
    }

    @Override
    protected void printSpecialPanel() {
        output.printf("%2d: %s \n", children.size() + 1, "Закрыть приложение");
    }

    protected Class<? extends Panel> getSpecialPanel(int inputNumber) {
        if (inputNumber == children.size() + 1) {
            appSession = null;
            return CloseApplicationPanel.class;
        } else throw new InputMismatchException();
    }
}