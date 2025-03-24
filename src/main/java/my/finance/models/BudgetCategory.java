package my.finance.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "budget_categories")
@Data
@NoArgsConstructor
public class BudgetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "wallet_id",referencedColumnName = "id", nullable = false)
    private Wallet wallet;

    @Column(name = "category_name", nullable = false)
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
}