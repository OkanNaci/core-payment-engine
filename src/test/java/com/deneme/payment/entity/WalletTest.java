package com.deneme.payment.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

import com.deneme.payment.exception.InsufficientBalanceException;

class WalletTest {

    @Test
    void shouldCreateWalletWithPositiveInitialBalance() {
        Wallet wallet = new Wallet(
                "wallet-1",
                new BigDecimal("100.00")
        );

        assertEquals("wallet-1", wallet.getId());
        assertEquals(
                0,
                new BigDecimal("100.00").compareTo(wallet.getBalance())
        );
    }

    @Test
    void shouldCreateWalletWithZeroInitialBalance() {
        Wallet wallet = new Wallet(
                "wallet-1",
                BigDecimal.ZERO
        );

        assertEquals(
                0,
                BigDecimal.ZERO.compareTo(wallet.getBalance())
        );
    }

    @Test
    void shouldRejectNullWalletId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Wallet(null, BigDecimal.ZERO)
        );
    }

    @Test
    void shouldRejectBlankWalletId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Wallet("   ", BigDecimal.ZERO)
        );
    }

    @Test
    void shouldRejectNullInitialBalance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Wallet("wallet-1", null)
        );
    }

    @Test
    void shouldRejectNegativeInitialBalance() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Wallet(
                        "wallet-1",
                        new BigDecimal("-1.00")
                )
        );
    }

    @Test
    void shouldDepositValidAmount() {
        Wallet wallet = new Wallet(
                "wallet-1",
                new BigDecimal("100.00")
        );

        wallet.deposit(new BigDecimal("50.00"));

        assertEquals(
                0,
                new BigDecimal("150.00").compareTo(wallet.getBalance())
        );
    }

    @Test
    void shouldRejectNullDeposit() {
        Wallet wallet = new Wallet(
                "wallet-1",
                new BigDecimal("100.00")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> wallet.deposit(null)
        );
    }

    @Test
    void shouldRejectZeroDeposit() {
        Wallet wallet = new Wallet(
                "wallet-1",
                new BigDecimal("100.00")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> wallet.deposit(BigDecimal.ZERO)
        );
    }

    @Test
    void shouldRejectNegativeDeposit() {
        Wallet wallet = new Wallet(
                "wallet-1",
                new BigDecimal("100.00")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> wallet.deposit(new BigDecimal("-50.00"))
        );
    }

    @Test
    void shouldWithdrawValidAmount() {
        Wallet wallet = new Wallet(
                "wallet-1",
                new BigDecimal("100.00")
        );

        wallet.withdraw(new BigDecimal("40.00"));

        assertEquals(
                0,
                new BigDecimal("60.00").compareTo(wallet.getBalance())
        );
    }

    @Test
    void shouldRejectNullWithdrawal() {
        Wallet wallet = new Wallet(
                "wallet-1",
                new BigDecimal("100.00")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> wallet.withdraw(null)
        );
    }

    @Test
    void shouldRejectZeroWithdrawal() {
        Wallet wallet = new Wallet(
                "wallet-1",
                new BigDecimal("100.00")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> wallet.withdraw(BigDecimal.ZERO)
        );
    }

    @Test
    void shouldRejectNegativeWithdrawal() {
        Wallet wallet = new Wallet(
                "wallet-1",
                new BigDecimal("100.00")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> wallet.withdraw(new BigDecimal("-50.00"))
        );
    }

    @Test
    void shouldRejectWithdrawalWhenBalanceIsInsufficient() {
        Wallet wallet = new Wallet(
                "wallet-1",
                new BigDecimal("100.00")
        );

        assertThrows(
                InsufficientBalanceException.class,
                () -> wallet.withdraw(new BigDecimal("150.00"))
        );
    }
}