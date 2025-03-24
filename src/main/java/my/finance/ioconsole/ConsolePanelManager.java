package my.finance.ioconsole;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("console")
public class ConsolePanelManager implements Manager {


    public void manage(Panel panel) {
        while (true) {
            panel.printLoginAndBalance();
            panel.printPanel();
            panel.action();
            panel = panel.nextPanel();
        }
    }
}
