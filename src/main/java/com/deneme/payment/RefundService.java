package com.deneme.payment;

import java.util.ArrayDeque;
import java.util.Deque;

public class RefundService {


    private final Deque<Order> refundStack;

    public RefundService() {
        this.refundStack = new ArrayDeque<>();
    }

    public void addRefundRequest(Order order) {
        refundStack.push(order);
    }

    public Order processNextRefund() {
        if (refundStack.isEmpty()) {
            return null;
        }
        return refundStack.pop();
    }

    public int getPendingRefundsCount() {
        return refundStack.size();
    }
}