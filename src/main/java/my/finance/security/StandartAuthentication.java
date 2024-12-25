package my.finance.security;

import my.finance.models.User;
import my.finance.repository.UserRepository;

import java.util.Optional;

public class StandartAuthentication implements Authentication {
    private final HashFunc STANDARTHASHFUNC = new SHA256HashFunc();
    private final UserRepository userRepository;

    public StandartAuthentication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<AppSession> authenticate(String login, String password) {
        Optional<User> foundUser = userRepository.findByLogin(login);
        if (foundUser.isPresent()
                && foundUser.get().getPassword().equals(STANDARTHASHFUNC.hash(password))) {
            return Optional.of(new AppSession(foundUser.get()));
        }
        return Optional.empty();
    }

    public HashFunc getHashFunc() {
        return STANDARTHASHFUNC;
    }
}
