package my.finance.service.wallet;

import my.finance.models.Wallet;
import my.finance.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    public double calculateBalance(int id) {
        return walletRepository.calculateBalance(id);
    }

    public Wallet calculateBalanceAndUpdate(Wallet wallet) {
        double calBalance = walletRepository.calculateBalance(wallet.getId());
        wallet.setBalance(calBalance);
        return walletRepository.save(wallet);
    }


    public void save(Wallet wallet) {
        walletRepository.save(wallet);
    }
}
