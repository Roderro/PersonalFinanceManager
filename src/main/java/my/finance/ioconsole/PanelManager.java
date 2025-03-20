package my.finance.ioconsole;


import org.springframework.stereotype.Component;

@Component
public class PanelManager implements Manager {


    public void manage(Panel panel) {
        while (true) {
            panel.printLoginAndBalance();
            panel.printPanel();
            panel.action();
            panel = panel.nextPanel();
        }
    }
}
