package com.deneme.payment;

import com.deneme.payment.exception.NoRefundPendingException;
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
            throw new NoRefundPendingException("Kritik Uyarı: İade edilecek herhangi bir işlem bulunamadı!");
        }
        return refundStack.pop();
    }

    public int getPendingRefundsCount() {
        return refundStack.size();
    }
}