package com.deneme.payment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentQueueManagerTest {

    @Test
    void shouldProcessOrdersInFifoOrder() {

        PaymentQueueManager queueManager = new PaymentQueueManager();
        Order order1 = new Order("ORD-001");
        Order order2 = new Order("ORD-002");
        Order order3 = new Order("ORD-003");

        queueManager.addOrderToQueue(order1);
        queueManager.addOrderToQueue(order2);
        queueManager.addOrderToQueue(order3);

        assertEquals(3, queueManager.getPendingOrdersCount(), "Kuyrukta 3 sipariş bekliyor olmalı!");

        Order firstProcessed = queueManager.getNextOrderToProcess();
        assertEquals("ORD-001", firstProcessed.getOrderId(), "İlk eklenen ORD-001 ilk çıkmalı (FIFO)!");

        Order secondProcessed = queueManager.getNextOrderToProcess();
        assertEquals("ORD-002", secondProcessed.getOrderId(), "İkinci sıradaki ORD-002 çıkmalı!");

        assertEquals(1, queueManager.getPendingOrdersCount(), "2 sipariş işlendikten sonra kuyrukta 1 sipariş kalmalı!");
    }

    @Test
    void shouldHandleEmptyQueueGracefully() {
        PaymentQueueManager queueManager = new PaymentQueueManager();

        Order processedOrder = queueManager.getNextOrderToProcess();

        assertNull(processedOrder, "Boş kuyruktan çekilen sipariş null olmalı!");
        assertTrue(queueManager.isQueueEmpty(), "Kuyruk boş (empty) durumunda olmalı!");
    }
}