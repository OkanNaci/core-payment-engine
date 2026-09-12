package com.deneme.payment;

import java.util.ArrayDeque;
import java.util.Queue;

public class PaymentQueueManager {

    private final Queue<Order> paymentQueue;

    public PaymentQueueManager() {
        this.paymentQueue = new ArrayDeque<>();
    }

    public void addOrderToQueue(Order order) {
        paymentQueue.offer(order);
    }

    public Order getNextOrderToProcess() {
        return paymentQueue.poll();
    }

    public int getPendingOrdersCount() {
        return paymentQueue.size();
    }

    public boolean isQueueEmpty() {
        return paymentQueue.isEmpty();
    }
}