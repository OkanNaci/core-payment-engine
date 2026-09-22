package com.deneme.payment;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal; // BigDecimal kütüphanesini ekledik

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {

    @Test
    void shouldCalculateTotalAndMarkAsPaid() {
        Order order = new Order("ORD-001");

        // Sayıları new BigDecimal("...") şeklinde String olarak veriyoruz
        order.addProduct(new Product("Mekanik Klavye", new BigDecimal("150.00")));
        order.addProduct(new Product("Oyuncu Mouse", new BigDecimal("100.00")));

        PaymentMethod pos = new GarantiPos();

        if (pos.processPayment(order.getTotalAmount())) {
            order.markAsPaid();
        }

        assertTrue(order.isPaid(), "Ödeme başarılıysa sipariş durumu değişmeli!");
    }
}