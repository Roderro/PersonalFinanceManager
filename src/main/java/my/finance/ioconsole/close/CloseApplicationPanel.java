package my.finance.ioconsole.close;

import my.finance.ioconsole.AbstractPanel;
import my.finance.ioconsole.Panel;
import my.finance.ioconsole.authentication.AuthenticationMainPanel;
import my.finance.security.AppSession;

public class CloseApplicationPanel extends AbstractPanel {

    public CloseApplicationPanel(AppSession appSession) {
        super(appSession);
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
        System.out.println("До скорых встреч");
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
