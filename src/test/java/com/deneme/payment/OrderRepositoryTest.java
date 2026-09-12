package com.deneme.payment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderRepositoryTest {

    @Test
    void shouldSaveAndFindOrder() {
        // Arrange
        OrderRepository repository = new OrderRepository();
        Order order = new Order("REPO-001");

        // Act
        repository.save(order);
        Order foundOrder = repository.findById("REPO-001");

        // Assert
        assertNotNull(foundOrder, "Sipariş veritabanında bulunmalı!");
        assertEquals("REPO-001", foundOrder.getOrderId(), "Bulunan siparişin ID'si eşleşmeli!");
    }

    @Test
    void shouldPreventDuplicatePayments() {
        // Arrange
        OrderRepository repository = new OrderRepository();
        Order order = new Order("REPO-002");
        repository.save(order);

        // Act
        repository.markOrderAsPaid("REPO-002");

        // Assert
        assertTrue(repository.isOrderPaid("REPO-002"), "Sipariş ödenmiş olarak işaretlenmeli!");
    }
}