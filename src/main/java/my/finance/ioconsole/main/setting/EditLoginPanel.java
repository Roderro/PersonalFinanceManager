package my.finance.ioconsole.main.setting;


import my.finance.exception.user.UsernameAlreadyExistsException;
import my.finance.ioconsole.AbstractPanel;
import my.finance.models.User;
import my.finance.service.user.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Profile("console")
public class EditLoginPanel extends AbstractPanel {
    static final String TEXT = "Изменить логин";
    private final UserService userService;

    public EditLoginPanel(UserService userService) {
        super();
        this.userService = userService;
    }

    @Override
    public void action() {
        output.print("Введите новый логин:");
        try {
            String newLogin = input.next();
            User updatedUser = userService.changeLogin(appSession.getUser().getId(), newLogin);
            appSession.setUser(updatedUser);
            output.println("Логин изменен на: " + newLogin);
        } catch (UsernameAlreadyExistsException e) {
            output.println(e.getMessage());
        } catch (Exception e) {
            output.println("Не удалось изменить логин!" + e.getMessage());
        }
    }
}

