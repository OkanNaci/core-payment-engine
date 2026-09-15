package com.deneme.payment;

import com.deneme.payment.exception.NoRefundPendingException;
import java.util.ArrayDeque;
import java.util.Deque;

public class RefundService {

    private Deque<Order> refundStack = new ArrayDeque<>();

    private final PaymentMethod paymentMethod;

    public RefundService(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void addRefundRequest(Order order) {
        if (order.getStatus() != OrderStatus.COMPLETED) {
            throw new IllegalArgumentException("İade işlemi reddedildi! Sadece ödemesi tamamlanmış siparişler iade edilebilir. Mevcut durum: " + order.getStatus());
        }
        refundStack.push(order);
    }

    public Order processNextRefund() {
        if (refundStack.isEmpty()) {
            throw new NoRefundPendingException("İade edilecek herhangi bir işlem bulunamadı!");
        }

        Order refundedOrder = refundStack.pop();

        boolean isRefundSuccessful = paymentMethod.processRefund(100.0);

        if (isRefundSuccessful) {
            refundedOrder.setStatus(OrderStatus.REFUNDED);
        } else {
            refundedOrder.setStatus(OrderStatus.FAILED);
        }

        return refundedOrder;
    }
}