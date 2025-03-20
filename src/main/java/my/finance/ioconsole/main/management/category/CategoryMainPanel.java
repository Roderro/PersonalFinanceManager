package my.finance.ioconsole.main.management.category;

import my.finance.ioconsole.AbstractMainPanel;
import my.finance.security.AppSession;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class CategoryMainPanel extends AbstractMainPanel {
    static final String TEXT = "Управление категориями";

    public CategoryMainPanel() {
        super();
    }

}
