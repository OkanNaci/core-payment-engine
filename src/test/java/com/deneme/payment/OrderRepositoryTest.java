package com.deneme.payment;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class OrderRepositoryTest {

    @Test
    void shouldSaveAndFindOrder() {
        OrderRepository repository = new OrderRepository();
        Order order = new Order("REPO-001");

        repository.save(order);
        Optional<Order> foundOrder = repository.findById("REPO-001");


        assertTrue(foundOrder.isPresent(), "Sipariş veritabanında bulunmalı!");

        assertEquals("REPO-001", foundOrder.get().getOrderId(), "Bulunan siparişin ID'si eşleşmeli!");
    }

    @Test
    void shouldPreventDuplicatePayments() {
        OrderRepository repository = new OrderRepository();
        Order order = new Order("REPO-002");
        repository.save(order);

        repository.markOrderAsPaid("REPO-002");

        assertTrue(repository.isOrderPaid("REPO-002"), "Sipariş ödenmiş olarak işaretlenmeli!");
    }
}