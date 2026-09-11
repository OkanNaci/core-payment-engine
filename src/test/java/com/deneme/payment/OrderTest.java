package com.deneme.payment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void shouldMarkOrderAsPaidWhenPaymentIsSuccessful() {
        // 1. HAZIRLIK: Bellekte sahte sipariş ve ödeme yöntemi oluştur
        Order testOrder = new Order("ORD-TEST", 250.0);
        PaymentMethod mockPayment = new GarantiPos();

        // 2. EYLEM: Ödemeyi çek ve başarılıysa siparişi "ödendi" olarak işaretle
        boolean isSuccess = mockPayment.processPayment(testOrder.getTotalAmount());
        if (isSuccess) {
            testOrder.markAsPaid();
        }

        // 3. İDDİA: Bilgisayara "Bunları kontrol et, yanlışsa sistemi durdur" komutu ver
        assertTrue(testOrder.isPaid(), "HATA: İşlem başarılı olduğu halde sipariş 'ödendi' yapılmadı!");
        assertEquals(250.0, testOrder.getTotalAmount(), "HATA: Sipariş tutarı izinsiz değişti!");
    }
}