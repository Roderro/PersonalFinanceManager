package my.finance.security;

import lombok.Getter;
import my.finance.models.User;
import my.finance.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class StandartAuthentication implements Authentication {
    @Getter
    private final HashFunc hashFunc;
    private final UserRepository userRepository;
    private final AppSession appSession;

    public StandartAuthentication(HashFunc hashFunc, UserRepository userRepository, AppSession appSession) {
        this.hashFunc = hashFunc;
        this.userRepository = userRepository;
        this.appSession = appSession;
    }

    @Override
    public Optional<AppSession> authenticate(String login, String password) {
        Optional<User> foundUser = userRepository.findByLogin(login);
        if (foundUser.isPresent()
                && foundUser.get().getPassword().equals(hashFunc.hash(password))) {
            appSession.setUser(foundUser.get());
            return Optional.of(appSession);
        }
        return Optional.empty();
    }

}
