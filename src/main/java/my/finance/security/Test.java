package my.finance.security;

import my.finance.repository.UserRepository;

public class Test {

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        StandartAuthentication standartAuthentication = new StandartAuthentication(userRepository);

    }
}
