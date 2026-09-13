package com.deneme.payment;

import com.deneme.payment.exception.NoRefundPendingException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RefundServiceTest {

    @Test
    void shouldProcessRefundsInLifoOrder() {
        // 1. Arrange (Hazırlık)
        RefundService refundService = new RefundService();
        Order order1 = new Order("ORD-001");
        Order order2 = new Order("ORD-002");
        Order order3 = new Order("ORD-003");

        // 2. Act
        refundService.addRefundRequest(order1);
        refundService.addRefundRequest(order2);
        refundService.addRefundRequest(order3);

        // 3. Assert (LIFO Kuralı)
        assertEquals(3, refundService.getPendingRefundsCount());
        assertEquals("ORD-003", refundService.processNextRefund().getOrderId());
        assertEquals("ORD-002", refundService.processNextRefund().getOrderId());
        assertEquals(1, refundService.getPendingRefundsCount());
    }

    @Test
    void shouldThrowExceptionWhenRefundingEmptyStack() {
        RefundService refundService = new RefundService();


        NoRefundPendingException thrownException = assertThrows(
                NoRefundPendingException.class,
                () -> refundService.processNextRefund(),
                "Boş yığından işlem çekilmeye çalışıldığında NoRefundPendingException fırlatılmalı!"
        );

        assertTrue(thrownException.getMessage().contains("İade edilecek herhangi bir işlem bulunamadı"));
    }
}