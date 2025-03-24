package my.finance.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
public class AppTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "wallet_id", referencedColumnName = "id", nullable = false)
    private Wallet wallet;

    @Column(name = "amount", nullable = false)
    private double amount;

    @ManyToOne()
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private BudgetCategory budgetCategory;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public AppTransaction(Wallet wallet, double amount, BudgetCategory budgetCategory, String description) {
        this.wallet = wallet;
        this.budgetCategory = budgetCategory;
        this.amount = setSignAmount(budgetCategory.isIncome(), amount);
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = setSignAmount(isIncome(), amount);
    }

    private double setSignAmount(boolean isIncome, double amount) {
        if (isIncome)
            return Math.abs(amount);
        else {
            return -Math.abs(amount);
        }
    }

    public boolean isIncome() {
        return this.budgetCategory.isIncome();
    }

}