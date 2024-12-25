package my.finance.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "transactions")
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

    public AppTransaction() {
    }

    public AppTransaction(Wallet wallet, double amount, BudgetCategory budgetCategory, String description) {
        this.wallet = wallet;
        this.budgetCategory = budgetCategory;
        if (budgetCategory.isIncome()) {
            this.amount = Math.abs(amount);
        } else {
            this.amount = -Math.abs(amount);
        }
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BudgetCategory getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(BudgetCategory budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIncome() {
        return this.budgetCategory.isIncome();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "AppTransaction{" +
                "budgetCategory=" + budgetCategory +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }
}