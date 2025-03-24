package my.finance.ioconsole.main.management.category;

import my.finance.ioconsole.AbstractMainPanel;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Profile("console")
public class CategoryMainPanel extends AbstractMainPanel {
    static final String TEXT = "Управление категориями";

    public CategoryMainPanel() {
        super();
    }

}
