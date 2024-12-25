package my.finance.security;

@FunctionalInterface
public interface HashFunc {
    String hash(String password);
}
