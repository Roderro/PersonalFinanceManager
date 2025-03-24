package my.finance.ioconsole.main.setting;

import my.finance.ioconsole.AbstractMainPanel;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Profile("console")
public class SettingMainPanel extends AbstractMainPanel {
    static final String TEXT = "Настройки";

    public SettingMainPanel() {
        super();
    }

}
