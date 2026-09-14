package com.deneme.payment;

import com.deneme.payment.exception.NoRefundPendingException;
import java.util.ArrayDeque;
import java.util.Deque;

public class RefundService {

    private Deque<Order> refundStack = new ArrayDeque<>();

    public void addRefundRequest(Order order) {
        if (order.getStatus() != OrderStatus.COMPLETED) {
            throw new IllegalArgumentException("İade işlemi reddedildi! Sadece ödemesi tamamlanmış (COMPLETED) siparişler iade edilebilir. Mevcut durum: " + order.getStatus());
        }
        refundStack.push(order);
    }

    public Order processNextRefund() {
        if (refundStack.isEmpty()) {
            throw new NoRefundPendingException("İade edilecek herhangi bir işlem bulunamadı!");
        }

        Order refundedOrder = refundStack.pop();

        refundedOrder.setStatus(OrderStatus.REFUNDED);

        return refundedOrder;
    }
}