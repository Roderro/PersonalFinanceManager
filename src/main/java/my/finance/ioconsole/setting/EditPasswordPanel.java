package my.finance.ioconsole.setting;

import my.finance.ioconsole.AbstractPanel;
import my.finance.security.AppSession;
import my.finance.security.Authentication;
import my.finance.security.StandartAuthentication;


public class EditPasswordPanel extends AbstractPanel {
    static final String TEXT = "Изменить пароль";
    private final Authentication authentication;


    public EditPasswordPanel(AppSession appSession) {
        super(appSession);
        this.authentication = new StandartAuthentication(userRepository);
    }

    @Override
    public void action() {
        output.print("Введите ваш пароль: ");
        String oldPassword = input.next();
        if (appSession.getUser().getPassword().equals(authentication.getHashFunc().hash(oldPassword))) {
            try {
                output.print("Введите новый пароль: ");
                String newPasswordHash = authentication.getHashFunc().hash(input.nextLine());
                appSession.getUser().setPassword(newPasswordHash);
                userRepository.update(appSession.getUser());
                output.println("Пароль изменен!");
            } catch (Exception e) {
                output.println("Не удалось поменять пароль");
            }
        } else {
            output.println("Введен не верный пароль");
        }
    }
}
