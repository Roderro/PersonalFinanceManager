package my.finance.models;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Wallets")
@Data
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
}
