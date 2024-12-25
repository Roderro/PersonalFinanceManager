package my.finance.security;

import my.finance.models.User;

import java.util.Optional;

public interface Authentication {

    Optional<AppSession> authenticate(String login, String password);

    HashFunc getHashFunc();

}
