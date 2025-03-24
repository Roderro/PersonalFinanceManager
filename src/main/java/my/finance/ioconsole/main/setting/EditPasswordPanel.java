package my.finance.ioconsole.main.setting;

import my.finance.ioconsole.AbstractPanel;
import my.finance.models.User;
import my.finance.service.user.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Profile("console")
public class EditPasswordPanel extends AbstractPanel {
    static final String TEXT = "Изменить пароль";
    private final UserService userService;


    public EditPasswordPanel(UserService userService) {
        super();
        this.userService = userService;
    }

    @Override
    public void action() {
        output.print("Введите ваш пароль: ");
        String oldPassword = input.next();
        if (userService.verificationPassword(appSession.getUser(), oldPassword)) {
            try {
                output.print("Введите новый пароль: ");
                String newPassword = input.nextLine();
                User updatedUser = userService.changePassword(appSession.getUser().getId(), newPassword);
                appSession.setUser(updatedUser);
                output.println("Пароль изменен!");
            } catch (Exception e) {
                output.println("Не удалось поменять пароль" + e.getMessage());
            }
        } else {
            output.println("Введен не верный пароль");
        }
    }
}
