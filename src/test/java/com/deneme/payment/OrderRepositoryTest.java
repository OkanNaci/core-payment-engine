package com.deneme.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;

    @Test
    void shouldSaveAndFindOrder() {
        Order order = new Order("REPO-001");
        repository.save(order);

        Optional<Order> foundOrder = repository.findById("REPO-001");

        assertTrue(foundOrder.isPresent(), "Sipariş PostgreSQL'de bulunmalı!");
        assertEquals("REPO-001", foundOrder.get().getOrderId(), "Bulunan siparişin ID'si eşleşmeli!");
    }
}