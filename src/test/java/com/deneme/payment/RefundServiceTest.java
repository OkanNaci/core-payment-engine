package com.deneme.payment;

import com.deneme.payment.exception.NoRefundPendingException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RefundServiceTest {

    @Test
    void shouldThrowExceptionWhenRefundingEmptyStack() {
        RefundService refundService = new RefundService();
        assertThrows(NoRefundPendingException.class, () -> {
            refundService.processNextRefund();
        });
    }

    @Test
    void shouldSuccessfullyProcessRefundAndChangeStatus() {
        RefundService refundService = new RefundService();
        Order order = new Order("ORD-123");

        order.setStatus(OrderStatus.COMPLETED);

        refundService.addRefundRequest(order);
        Order processedOrder = refundService.processNextRefund();

        assertEquals(OrderStatus.REFUNDED, processedOrder.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenRefundingPendingOrder() {
        RefundService refundService = new RefundService();
        Order order = new Order("ORD-999");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            refundService.addRefundRequest(order);
        });

        assertTrue(exception.getMessage().contains("İade işlemi reddedildi"));
    }
}