package com.deneme.payment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RefundServiceTest {

    @Test
    void shouldProcessRefundsInLifoOrder() {
        RefundService refundService = new RefundService();
        Order order1 = new Order("ORD-001");
        Order order2 = new Order("ORD-002");
        Order order3 = new Order("ORD-003");

        refundService.addRefundRequest(order1);
        refundService.addRefundRequest(order2);
        refundService.addRefundRequest(order3);

        assertEquals(3, refundService.getPendingRefundsCount(), "Yığında 3 iade talebi olmalı!");

        Order firstRefunded = refundService.processNextRefund();
        assertEquals("ORD-003", firstRefunded.getOrderId(), "En SON iptal edilen (ORD-003) İLK iade edilmeli (LIFO)!");

        Order secondRefunded = refundService.processNextRefund();
        assertEquals("ORD-002", secondRefunded.getOrderId(), "Yığındaki bir sonraki işlem ORD-002 olmalı!");

        assertEquals(1, refundService.getPendingRefundsCount(), "2 iade yapıldıktan sonra yığında 1 işlem kalmalı!");
    }

    @Test
    void shouldHandleEmptyStackGracefully() {
        RefundService refundService = new RefundService();

        Order processedRefund = refundService.processNextRefund();

        assertNull(processedRefund, "Boş yığından çekilen işlem null olmalı!");
    }
}