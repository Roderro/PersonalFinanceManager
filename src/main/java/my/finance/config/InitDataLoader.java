package my.finance.config;

import lombok.RequiredArgsConstructor;
import my.finance.models.AppTransaction;
import my.finance.models.BudgetCategory;
import my.finance.models.User;
import my.finance.repository.AppTransactionRepository;
import my.finance.repository.BudgetCategoryRepository;
import my.finance.security.AppSession;
import my.finance.service.authentication.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
@Profile("test")
public class InitDataLoader implements CommandLineRunner {
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final AppTransactionRepository appTransactionRepository ;
    private final AuthenticationService authenticationService;
    private final AppSession appSession;


    @Override
    public void run(String... args) throws Exception {
        String newLogin = "Test";
        String newPassword = "1234";
        User newUser = authenticationService.registerNewUser(newLogin, newPassword);
        appSession.setUser(newUser);
        BudgetCategory work = new BudgetCategory(newUser.getWallet(), "Работа");
        BudgetCategory some = new BudgetCategory(newUser.getWallet(), "Подработка");
        BudgetCategory food = new BudgetCategory(newUser.getWallet(), "еда", 15000);
        BudgetCategory game = new BudgetCategory(newUser.getWallet(), "Развлечения", 5000);
        AppTransaction ovanz = new AppTransaction(newUser.getWallet(), 40000, work, "Аванс декабрь");
        AppTransaction zarplata = new AppTransaction(newUser.getWallet(), 50000, work, "ЗП декабрь");
        AppTransaction sushi = new AppTransaction(newUser.getWallet(), 1500, food, "Заказали суши");
        try {
            budgetCategoryRepository.save(work);
            budgetCategoryRepository.save(some);
            budgetCategoryRepository.save(food);
            budgetCategoryRepository.save(game);
            appTransactionRepository.save(ovanz);
            appTransactionRepository.save(zarplata);
            appTransactionRepository.save(sushi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
