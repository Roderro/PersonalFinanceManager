package my.finance.repository;

import my.finance.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    @Query("SELECT coalesce(SUM(ap.amount),0) " +
            "FROM AppTransaction ap " +
            "WHERE ap.wallet.id = :walletId")
    double calculateBalance(int walletId);
}
