package my.finance.security;

import my.finance.models.User;
import my.finance.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class StandartAuthentication implements Authentication {
    private final HashFunc hashFunc;
    private final UserRepository userRepository;

    public StandartAuthentication(HashFunc hashFunc, UserRepository userRepository) {
        this.hashFunc = hashFunc;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<AppSession> authenticate(String login, String password) {
        Optional<User> foundUser = userRepository.findByLogin(login);
        if (foundUser.isPresent()
                && foundUser.get().getPassword().equals(hashFunc.hash(password))) {
            return Optional.of(new AppSession(foundUser.get()));
        }
        return Optional.empty();
    }

    public HashFunc getHashFunc() {
        return hashFunc;
    }
}
