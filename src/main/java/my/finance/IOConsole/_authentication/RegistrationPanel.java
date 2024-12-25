package my.finance.IOConsole._authentication;

import jakarta.persistence.PersistenceException;
import my.finance.IOConsole.AbstractPanel;
import my.finance.IOConsole.MainPanel;
import my.finance.models.User;
import my.finance.security.AppSession;
import my.finance.security.Authentication;
import my.finance.security.StandartAuthentication;

public class RegistrationPanel extends AbstractPanel {
    static final String TEXT = "Регистрация";

    private final Authentication authentication;

    public RegistrationPanel(AppSession appSession) {
        super(appSession);
        this.authentication = new StandartAuthentication(userRepository);
    }

    public RegistrationPanel(AppSession appSession, Authentication authentication) {
        super(appSession);
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
            userRepository.add(newUser);
            appSession = new AppSession(newUser);
            nextPanelClass = MainPanel.class;
        } catch (PersistenceException e) {
            output.println("Логин уже существует!");
        } catch (Exception e) {
            System.out.println("Что-то пошло не так попробуйте снова!");
        }
    }
}

