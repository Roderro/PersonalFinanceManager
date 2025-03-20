package my.finance.ioconsole.authentication;

import my.finance.ioconsole.AbstractPanel;
import my.finance.ioconsole.main.MainPanel;
import my.finance.security.AppSession;
import my.finance.security.Authentication;
import my.finance.security.StandartAuthentication;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class LoginPanel extends AbstractPanel {
    static final String TEXT = "Вход";
    private final Authentication authentication;

    public LoginPanel(Authentication authentication) {
        super();
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
