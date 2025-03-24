package my.finance.exception.user;

public class IncorrectUserCredentialException extends RuntimeException {
    public IncorrectUserCredentialException(String message) {
        super(message);
    }
}
