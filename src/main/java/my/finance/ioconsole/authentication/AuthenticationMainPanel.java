package my.finance.ioconsole.authentication;

import my.finance.ioconsole.AbstractMainPanel;
import my.finance.ioconsole.close.CloseApplicationPanel;
import my.finance.ioconsole.Panel;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;


@Component
@Profile("console")
public class AuthenticationMainPanel extends AbstractMainPanel {
    static final String TEXT = "Аутентификация";


    public AuthenticationMainPanel() {
        super();
    }

    @Override
    protected void printSpecialPanel() {
        output.printf("%2d: %s \n", children.size() + 1, "Закрыть приложение");
    }

    @Override
    protected Class<? extends Panel> getSpecialPanel(int inputNumber) {
        if (inputNumber == children.size() + 1) {
            return CloseApplicationPanel.class;
        } else throw new InputMismatchException();
    }

    @Override
    protected Class<? extends Panel> getClassParentPanel() {
        return this.getClass();
    }
}
