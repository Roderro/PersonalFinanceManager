package my.finance.models;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Wallets")
public class Wallet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    @Column(name = "balance")
    private double balance;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "transactions")
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.PERSIST)
    private List<AppTransaction> appTransactions;

    @Column(name = "budgetCategories")
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.PERSIST)
    private List<BudgetCategory> budgetCategories;

    public Wallet() {
    }

    public Wallet(double balance, User owner) {
        this.balance = balance;
        this.owner = owner;
        this.appTransactions = new ArrayList<>();
        this.budgetCategories = new ArrayList<>();
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<AppTransaction> getAppTransactions() {
        return appTransactions;
    }

    public void setAppTransactions(List<AppTransaction> appTransactions) {
        this.appTransactions = appTransactions;
    }

    public List<BudgetCategory> getBudgetCategories() {
        return budgetCategories;
    }

    public void setBudgetCategories(List<BudgetCategory> budgetCategories) {
        this.budgetCategories = budgetCategories;
    }

    public void addTransaction(AppTransaction appTransaction) {
        if (appTransactions != null) {
            appTransaction.setWallet(this);
            appTransactions.add(appTransaction);
            if (appTransaction.isIncome()) {
                balance += appTransaction.getAmount();
            } else {
                balance -= appTransaction.getAmount();
            }
        }
    }

    public void addBudgetCategory(BudgetCategory budgetCategory) {
        if (budgetCategories != null) {
            budgetCategory.setWallet(this);
            budgetCategories.add(budgetCategory);
        }
    }
}
