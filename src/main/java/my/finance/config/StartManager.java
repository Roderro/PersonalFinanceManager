package my.finance.config;


import lombok.RequiredArgsConstructor;
import my.finance.ioconsole.Manager;
import my.finance.ioconsole.authentication.AuthenticationMainPanel;
import my.finance.ioconsole.main.MainPanel;
import my.finance.security.AppSession;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@RequiredArgsConstructor
public class StartManager implements CommandLineRunner {
    private final AppSession appSession;
    private final MainPanel mainPanel;
    private final AuthenticationMainPanel authenticationMainPanel;
    private final Manager manager;

    @Override
    public void run(String... args) throws Exception {
        if (appSession.getUser() == null) {
            manager.manage(authenticationMainPanel);
        } else {
            manager.manage(mainPanel);
        }
    }
}
