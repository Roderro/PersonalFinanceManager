package my.finance.ioconsole.main.setting;

import my.finance.ioconsole.AbstractPanel;
import my.finance.security.AppSession;
import my.finance.security.Authentication;
import my.finance.security.StandartAuthentication;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class EditPasswordPanel extends AbstractPanel {
    static final String TEXT = "Изменить пароль";
    private final Authentication authentication;


    public EditPasswordPanel(Authentication authentication) {
        super();
        this.authentication = authentication;
    }

    @Override
    public void action() {
        output.print("Введите ваш пароль: ");
        String oldPassword = input.next();
        String oldPasswordHash = appSession.getUser().getPassword();
        if (oldPasswordHash.equals(authentication.getHashFunc().hash(oldPassword))) {
            try {
                output.print("Введите новый пароль: ");
                String newPasswordHash = authentication.getHashFunc().hash(input.nextLine());
                appSession.getUser().setPassword(newPasswordHash);
                userRepository.save(appSession.getUser());
                output.println("Пароль изменен!");
            } catch (Exception e) {
                appSession.getUser().setPassword(oldPasswordHash);
                output.println("Не удалось поменять пароль");
            }
        } else {
            output.println("Введен не верный пароль");
        }
    }
}
