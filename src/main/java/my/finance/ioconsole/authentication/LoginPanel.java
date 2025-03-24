package my.finance.ioconsole.authentication;

import my.finance.exception.user.IncorrectUserCredentialException;
import my.finance.exception.user.UserNotFoundException;
import my.finance.ioconsole.AbstractPanel;
import my.finance.ioconsole.main.MainPanel;
import my.finance.security.AppSession;
import my.finance.service.authentication.AuthenticationService;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Profile("console")
public class LoginPanel extends AbstractPanel {
    static final String TEXT = "Вход";
    private final AuthenticationService authenticationService;

    public LoginPanel(AuthenticationService authenticationService) {
        super();
        this.authenticationService = authenticationService;
    }

    @Override
    public void action() {
        output.print("Введите ваш логин: ");
        String login = input.next();
        output.print("Введите ваш пароль: ");
        String password = input.next();
        try {
            AppSession session = authenticationService.getAuthentication(login, password);
            nextPanelClass = MainPanel.class;
        } catch (UserNotFoundException | IncorrectUserCredentialException e) {
            output.println(e.getMessage());
        }
    }
}
