package my.finance.security;

import java.util.Optional;

public interface Authentication {

    Optional<AppSession> authenticate(String login, String password);

    HashFunc getHashFunc();

}
