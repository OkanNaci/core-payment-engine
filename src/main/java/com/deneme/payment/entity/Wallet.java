package com.deneme.payment.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import org.hibernate.annotations.Check;
import com.deneme.payment.exception.InsufficientBalanceException;

@Entity
@Table(name="wallets")
@Check(constraints = "balance >= 0")
public class Wallet {

    @Id
    @Column(name="id")
    private String id ;

    @Column(nullable = false,precision = 19 , scale = 4)
    private BigDecimal balance;

    protected Wallet(){}

    public Wallet(String id, BigDecimal initialBalance) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Wallet id cannot be blank.");
        }

        if (initialBalance == null) {
            throw new IllegalArgumentException("Initial balance cannot be null.");
        }

        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }

        this.id = id;
        this.balance = initialBalance;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    private void validatePositiveAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null.");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
    }

    public void withdraw(BigDecimal amount) {
        validatePositiveAmount(amount);

        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }

        balance = balance.subtract(amount);
    }

    public void deposit(BigDecimal amount) {
        validatePositiveAmount(amount);
        balance = balance.add(amount);
    }
}
