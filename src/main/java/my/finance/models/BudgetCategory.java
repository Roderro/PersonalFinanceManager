package my.finance.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "budget_categories")
public class BudgetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "wallet_id",referencedColumnName = "id", nullable = false)
    private Wallet wallet;

    @Column(name = "category_name", nullable = false, unique = true)
    private String categoryName;

    @Column(name = "is_income", nullable = false)
    private boolean isIncome;

    @Column(name = "budget_limit", nullable = false)
    private double budgetLimit;

    @Column(name = "transactions")
    @OneToMany(mappedBy = "budgetCategory",cascade = CascadeType.REMOVE)
    private List<AppTransaction> appTransactions;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public BudgetCategory() {
    }


    //конструктор категорий доходов
    public BudgetCategory(Wallet wallet, String categoryName) {
        this.wallet = wallet;
        this.categoryName = categoryName;
        this.isIncome = true;
    }


    //конструктор категорий расходов
    public BudgetCategory(Wallet wallet, String categoryName, double budgetLimit) {
        this.wallet = wallet;
        this.categoryName = categoryName;
        this.budgetLimit = budgetLimit;
        this.isIncome = false;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getBudgetLimit() {
        return budgetLimit;
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

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }

    public List<AppTransaction> getTransactions() {
        return appTransactions;
    }

    public void setTransactions(List<AppTransaction> appTransactions) {
        this.appTransactions = appTransactions;
    }

    public void setBudgetLimit(double budgetLimit) {
        this.budgetLimit = budgetLimit;
    }
}