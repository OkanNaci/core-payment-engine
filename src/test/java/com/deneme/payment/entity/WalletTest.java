package com.deneme.payment.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

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
}