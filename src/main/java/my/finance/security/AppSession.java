package my.finance.security;

import my.finance.models.User;
import my.finance.models.Wallet;

public class AppSession {
    User user;

    public AppSession(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Wallet getWallet(){
        return user.getWallet();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
