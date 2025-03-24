package my.finance.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.finance.models.User;
import my.finance.models.Wallet;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
public class AppSession {
    User user;

    public AppSession(User user) {
        this.user = user;
    }

    public void logout() {
        this.user = null;
    }

    public Wallet getWallet() {
        return user.getWallet();
    }
}
