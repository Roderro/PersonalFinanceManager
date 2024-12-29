package my.finance.ioconsole;

import my.finance.security.AppSession;

public class CloseApplicationPanel extends AbstractPanel {

    public CloseApplicationPanel(AppSession appSession) {
        super(appSession);
    }

    @Override
    public final void printLoginAndBalance() {
    }

    @Override
    public void printPanel() {
        System.out.println("До скорых встреч");
    }


    @Override
    public void action() {
        try {
            input.close();
        }catch (Exception _){
        }
        System.exit(0);
    }

}
