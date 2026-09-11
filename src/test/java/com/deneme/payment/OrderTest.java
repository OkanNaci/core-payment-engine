package com.deneme.payment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void shouldMarkOrderAsPaidWhenPaymentIsSuccessful() {
        Order testOrder = new Order("ORD-TEST", 250.0);
        PaymentMethod mockPayment = new GarantiPos();

        boolean isSuccess = mockPayment.processPayment(testOrder.getTotalAmount());
        if (isSuccess) {
            testOrder.markAsPaid();
        }

        assertTrue(testOrder.isPaid(), "HATA: İşlem başarılı olduğu halde sipariş 'ödendi' yapılmadı!");
        assertEquals(250.0, testOrder.getTotalAmount(), "HATA: Sipariş tutarı izinsiz değişti!");
    }
}