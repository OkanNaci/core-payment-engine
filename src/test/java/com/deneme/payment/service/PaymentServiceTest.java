package com.deneme.payment.service;

import com.deneme.payment.entity.Order;
import com.deneme.payment.entity.OrderStatus;
import com.deneme.payment.entity.Product;
import com.deneme.payment.entity.Wallet;
import com.deneme.payment.repository.OrderRepository;
import com.deneme.payment.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testTransactionRollbackOnCrash() {
        String walletId = "WALLET-" + UUID.randomUUID().toString().substring(0, 5);
        Wallet wallet = new Wallet(walletId, new BigDecimal("5000.00"));
        walletRepository.save(wallet);

        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 5);
        Order order = new Order(orderId);
        Product laptop = new Product("Laptop", new BigDecimal("2000.00"));
        order.addProduct(laptop);
        orderRepository.save(order);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentService.processPayment(order.getOrderId(), wallet.getId(), true);
        });

        assertTrue(exception.getMessage().contains("Beklenmeyen sistem hatası"));

        Wallet dbWallet = walletRepository.findById(walletId).get();
        Order dbOrder = orderRepository.findById(orderId).get();

        assertEquals(0, new BigDecimal("5000.00").compareTo(dbWallet.getBalance()),
                "Cüzdan bakiyesi ROLLBACK edilmedi! Para kayboldu!");

        assertEquals(OrderStatus.PENDING, dbOrder.getStatus(),
                "Sipariş iptal edilmedi, onaylanmış olarak kaldı!");

        System.out.println("✅ TEST BAŞARILI: Sistem çöktü, @Transactional devreye girdi ve müşterinin 5000 TL'si kurtarıldı!");
    }
}