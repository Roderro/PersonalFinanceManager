package my.finance.ioconsole.authentication;

import jakarta.persistence.PersistenceException;
import my.finance.ioconsole.AbstractPanel;
import my.finance.ioconsole.main.MainPanel;
import my.finance.models.User;
import my.finance.security.AppSession;
import my.finance.security.Authentication;
import my.finance.security.StandartAuthentication;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class RegistrationPanel extends AbstractPanel {
    static final String TEXT = "Регистрация";

    private final Authentication authentication;

    public RegistrationPanel(Authentication authentication) {
        super();
        this.authentication = authentication;
    }


    @Override
    public void action() {
        output.print("Введите ваш логин: ");
        String newLogin = input.next();
        output.print("Введите ваш пароль: ");
        String newPassword = input.next();
        String hashNewPassword = authentication.getHashFunc().hash(newPassword);
        User newUser = new User(newLogin, hashNewPassword);
        try {
            userRepository.save(newUser);
            appSession.setUser(newUser);
            nextPanelClass = MainPanel.class;
        } catch (PersistenceException e) {
            output.println("Логин уже существует!");
        } catch (Exception e) {
            System.out.println("Что-то пошло не так попробуйте снова!");
        }
    }
}

