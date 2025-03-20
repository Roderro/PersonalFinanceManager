package my.finance.ioconsole.main.management;

import my.finance.ioconsole.AbstractMainPanel;
import my.finance.security.AppSession;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class ManagementMainPanel extends AbstractMainPanel {
    static final String TEXT = "Управление финансами";

    public ManagementMainPanel() {
        super();
    }

}
