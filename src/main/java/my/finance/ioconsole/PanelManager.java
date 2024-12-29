package my.finance.ioconsole;


/**
* Класс PanelManager отвечает за управление жизненным циклом и взаимодействием различных пользовательских панелей посредством непрерывного цикла.
 * Отображение пользовательской информации, рендеринг содержимого панели и выполнения действия панели.
 */
public class PanelManager {
    private Panel panel;


    public PanelManager(Panel panel) {
        this.panel = panel;
    }

    public void manage() {
        while (true) {
            panel.printLoginAndBalance();
            panel.printPanel();
            panel.action();
            panel = panel.nextPanel();
        }
    }
}
