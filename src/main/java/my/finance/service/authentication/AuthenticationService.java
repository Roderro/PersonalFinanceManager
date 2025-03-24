package my.finance.service.authentication;

import my.finance.exception.user.IncorrectUserCredentialException;
import my.finance.exception.user.UserCreatedException;
import my.finance.exception.user.UserNotFoundException;
import my.finance.exception.user.UsernameAlreadyExistsException;
import my.finance.models.User;
import my.finance.repository.UserRepository;
import my.finance.security.AppSession;
import my.finance.security.Authentication;
import my.finance.security.HashFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthenticationService {
    private final HashFunc hashFunc;
    private final AppSession appSession;
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(HashFunc hashFunc, Authentication authentication,
                                 AppSession appSession, UserRepository userRepository) {
        this.hashFunc = hashFunc;
        this.appSession = appSession;
        this.userRepository = userRepository;
    }

    public AppSession getAuthentication(String login, String password) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с данным логином не найден"));
        if (user.getPassword().equals(hashFunc.hash(password))) {
            appSession.setUser(user);
            return appSession;
        } else {
            throw new IncorrectUserCredentialException("Неверный логин или пароль");
        }
    }

    @Transactional
    public User registerNewUser(String login, String password) {
        String hashNewPassword = getHash(password);
        User newUser = new User(login, hashNewPassword);
        try {
            return userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyExistsException("Логин уже используется");
        } catch (DataAccessException e) {
            throw new UserCreatedException("Ошибка создания пользователя");
        }
    }

    private String getHash(String password) {
        return hashFunc.hash(password);
    }

}
