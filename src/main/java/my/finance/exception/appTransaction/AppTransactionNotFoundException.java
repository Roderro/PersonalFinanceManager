package my.finance.exception.appTransaction;

public class AppTransactionNotFoundException extends RuntimeException {
    public AppTransactionNotFoundException(String message) {
        super(message);
    }
}
