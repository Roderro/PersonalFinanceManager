package my.finance.ioconsole.authentication;

import my.finance.exception.user.UserCreatedException;
import my.finance.exception.user.UsernameAlreadyExistsException;
import my.finance.ioconsole.AbstractPanel;
import my.finance.ioconsole.main.MainPanel;
import my.finance.models.User;
import my.finance.service.authentication.AuthenticationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("console")
public class RegistrationPanel extends AbstractPanel {
    static final String TEXT = "Регистрация";
    private final AuthenticationService authenticationService;

    public RegistrationPanel(AuthenticationService authenticationService) {
        super();
        this.authenticationService = authenticationService;
    }


    @Override
    public void action() {
        output.print("Введите ваш логин: ");
        String newLogin = input.next();
        output.print("Введите ваш пароль: ");
        String newPassword = input.next();
        try {
            User newUser = authenticationService.registerNewUser(newLogin, newPassword);
            appSession.setUser(newUser);
            nextPanelClass = MainPanel.class;
        } catch (UsernameAlreadyExistsException e) {
            output.println(e.getMessage());
        } catch (UserCreatedException e) {
            output.println(e.getMessage() + ", попробуйте снова");
        }
    }
}

