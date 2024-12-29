package my.finance.ioconsole.main.setting;

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
        String oldLogin = appSession.getUser().getLogin();
        try {
            String newLogin = input.next();
            appSession.getUser().setLogin(newLogin);
            userRepository.update(appSession.getUser());
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

