package my.finance.ioconsole.main.setting;

import my.finance.ioconsole.AbstractMainPanel;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class SettingMainPanel extends AbstractMainPanel {
    static final String TEXT = "Настройки";

    public SettingMainPanel() {
        super();
    }

}
