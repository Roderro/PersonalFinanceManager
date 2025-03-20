package my.finance.ioconsole.main.setting;

import jakarta.persistence.PersistenceException;
import my.finance.ioconsole.AbstractPanel;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class EditLoginPanel extends AbstractPanel {
    static final String TEXT = "Изменить логин";


    public EditLoginPanel() {
        super();
    }

    @Override
    public void action() {
        output.print("Введите новый логин:");
        String oldLogin = appSession.getUser().getLogin();
        try {
            String newLogin = input.next();
            appSession.getUser().setLogin(newLogin);
            userRepository.save(appSession.getUser());
            output.println("Логин изменен!");
        } catch (PersistenceException e) {
            output.println("Логин уже существует!");
            appSession.getUser().setLogin(oldLogin);
        } catch (Exception e) {
            appSession.getUser().setLogin(oldLogin);
            output.println("Не удалось изменить логин!");
        }
    }
}

