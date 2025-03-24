package my.finance.service.user;

import my.finance.exception.user.UserCreatedException;
import my.finance.exception.user.UserNotFoundException;
import my.finance.exception.user.UsernameAlreadyExistsException;
import my.finance.models.User;
import my.finance.repository.UserRepository;
import my.finance.security.HashFunc;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final HashFunc hashFunc;

    public UserService(UserRepository userRepository, HashFunc hashFunc) {
        this.userRepository = userRepository;
        this.hashFunc = hashFunc;
    }

    @Transactional(readOnly = true)
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с " + id + " не был найден"));
    }


    public User updateUser(User user) {
        try {

            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyExistsException("Логин уже используется");
        } catch (DataAccessException e) {
            throw new UserCreatedException("Ошибка создания пользователя " + e.getMessage());
        }
    }

    public boolean verificationPassword(User user, String password) {
        return hashFunc.hash(password).equals(user.getPassword());
    }

    public User changePassword(int userId, String newPassword) {
        User user = getUserById(userId);
        user.setPassword(hashFunc.hash(newPassword));
        return userRepository.save(user);
    }

    public User changeLogin(int userId, String newLogin) {
        User user = getUserById(userId);
        user.setLogin(newLogin);
        return userRepository.save(user);
    }
}
