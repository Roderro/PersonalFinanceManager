package my.finance.ioconsole._authentication;

import my.finance.ioconsole.AbstractPanel;
import my.finance.ioconsole.MainPanel;
import my.finance.security.AppSession;
import my.finance.security.Authentication;
import my.finance.security.StandartAuthentication;

import java.util.Optional;

public class LoginPanel extends AbstractPanel {
    static final String TEXT = "Войти";
    private final Authentication authentication;

    public LoginPanel(AppSession appSession) {
        super(appSession);
        this.authentication = new StandartAuthentication(userRepository);
    }

    public LoginPanel(AppSession appSession, Authentication authentication) {
        super(appSession);
        this.authentication = authentication;
    }



    @Override
    public void action() {
        output.print("Введите ваш логин: ");
        String login = input.next();
        output.print("Введите ваш пароль: ");
        String password = input.next();
        Optional<AppSession> newSession = authentication.authenticate(login, password);
        if (newSession.isPresent()) {
            this.appSession = newSession.get();
            nextPanelClass = MainPanel.class;
            return;
        }
        output.println("Введены не верные данные!");
    }
}
