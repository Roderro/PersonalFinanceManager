package my.finance.ioconsole.setting;

import jakarta.persistence.PersistenceException;
import my.finance.ioconsole.AbstractPanel;
import my.finance.security.AppSession;

public class EditLoginPanel extends AbstractPanel {
    static final String TEXT = "Изменить логин";


    public EditLoginPanel(AppSession appSession) {
        super(appSession);
    }

    @Override
    public void action() {
        output.print("Введите новый логин:");
        try {
            String newLogin = input.next();
            appSession.getUser().setLogin(newLogin);
            userRepository.update(appSession.getUser());
            output.println("Логин изменен!");
        } catch (PersistenceException e) {
            output.println("Логин уже существует!");
        } catch (Exception e) {
            output.println("Не удалось изменить логин!");
        }
    }
}

