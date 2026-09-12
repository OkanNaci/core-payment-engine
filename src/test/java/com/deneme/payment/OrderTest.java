package com.deneme.payment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {

    @Test
    void shouldCalculateTotalAndMarkAsPaid() {
        Order order = new Order("ORD-001");

        order.addProduct(new Product("Mekanik Klavye", 150.0));
        order.addProduct(new Product("Oyuncu Mouse", 100.0));

        PaymentMethod pos = new GarantiPos();

        if (pos.processPayment(order.getTotalAmount())) {
            order.markAsPaid();
        }

        assertTrue(order.isPaid(), "Ödeme başarılıysa sipariş durumu değişmeli!");
    }

}