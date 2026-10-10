package com.deneme.payment.entity;

import com.deneme.payment.service.strategy.GarantiPos;
import com.deneme.payment.service.strategy.PaymentMethod;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    void shouldCalculateTotalAndMarkAsPaid() {
        Order order = new Order("ORD-001");

        order.addProduct(
                new Product(
                        "Mekanik Klavye",
                        new BigDecimal("150.00")
                )
        );

        order.addProduct(
                new Product(
                        "Oyuncu Mouse",
                        new BigDecimal("100.00")
                )
        );

        PaymentMethod pos = new GarantiPos();

        if (pos.processPayment(order.getTotalAmount())) {
            order.markAsPaid();
        }

        assertTrue(
                order.isPaid(),
                "Ödeme başarılıysa sipariş durumu değişmeli!"
        );
    }

    @Test
    void shouldRejectNullOrderId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Order(null)
        );
    }

    @Test
    void shouldRejectBlankOrderId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Order("   ")
        );
    }

    @Test
    void shouldRejectNullProduct() {
        Order order = new Order("ORD-001");

        assertThrows(
                IllegalArgumentException.class,
                () -> order.addProduct(null)
        );
    }

    @Test
    void shouldNotAllowCartModificationFromOutside() {
        Order order = new Order("ORD-001");

        Product product = new Product(
                "Laptop",
                new BigDecimal("1000.00")
        );

        order.addProduct(product);

        assertThrows(
                UnsupportedOperationException.class,
                () -> order.getCart().add(
                        new Product(
                                "Mouse",
                                new BigDecimal("500.00")
                        )
                )
        );
    }
}