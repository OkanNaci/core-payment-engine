package com.deneme.payment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReceiptGeneratorTest {

    @Test
    void shouldGenerateReceiptForPendingOrder() {

        Order order = new Order("REC-001");
        ReceiptGenerator generator = new ReceiptGenerator();


        String receipt = generator.generateReceipt(order);


        assertNotNull(receipt, "Fiş metni boş (null) olamaz!");
        assertTrue(receipt.contains("Order ID: REC-001"), "Fişin içinde sipariş ID'si geçmeli!");
        assertTrue(receipt.contains("Status: PENDING"), "Ödenmemiş sipariş için PENDING yazmalı!");
    }

    @Test
    void shouldGenerateReceiptForPaidOrder() {
        Order order = new Order("REC-002");
        order.markAsPaid();
        ReceiptGenerator generator = new ReceiptGenerator();

        String receipt = generator.generateReceipt(order);

        assertTrue(receipt.contains("Status: PAID"), "Ödenmiş sipariş için PAID yazmalı!");
        assertFalse(receipt.contains("PENDING"), "Ödenmiş siparişte kesinlikle PENDING yazmamalı!");
    }
}