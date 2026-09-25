package com.deneme.payment;


import jakarta.persistence.*;
import java.math.BigDecimal;
import org.hibernate.annotations.Check;

@Entity
@Table(name="wallets")
@Check(constraints = "balance >= 0")
public class Wallet {

    @Id
    @Column(name="id")
    private String id ;

    @Column(nullable = false,precision = 19 , scale = 4)
    private BigDecimal balance;

    protected Wallet(){};

    public Wallet(String id,  BigDecimal initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void withdraw(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new RuntimeException("Yetersiz bakiye! İşlem iptal ediliyor.");
        }
        this.balance = this.balance.subtract(amount);
    }
    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

}
