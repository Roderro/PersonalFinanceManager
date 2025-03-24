package my.finance.ioconsole.close;

import my.finance.ioconsole.AbstractPanel;
import my.finance.ioconsole.Panel;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Profile("console")
public class CloseApplicationPanel extends AbstractPanel {

    public CloseApplicationPanel() {
        super();
    }

    @Override
    public final void printLoginAndBalance() {
    }

    @Override
    protected Class<? extends Panel> getClassParentPanel() {
        return null;
    }

    @Override
    public void printPanel() {
        output.println("До скорых встреч");
    }


    @Override
    public void action() {
        try {
            input.close();
        } catch (Exception _) {
        }
        System.exit(0);
    }

}
