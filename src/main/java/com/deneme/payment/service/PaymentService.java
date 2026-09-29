package com.deneme.payment.service;

import com.deneme.payment.entity.Order;
import com.deneme.payment.entity.Wallet;
import com.deneme.payment.repository.OrderRepository;
import com.deneme.payment.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class PaymentService {

    private final WalletRepository walletRepository;
    private final OrderRepository orderRepository;

    public PaymentService(WalletRepository walletRepository, OrderRepository orderRepository) {
        this.walletRepository = walletRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void processPayment(String orderId, String walletId, boolean simulateCrash) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı!"));

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Cüzdan bulunamadı!"));

        BigDecimal totalAmount = order.getTotalAmount();

        wallet.withdraw(totalAmount);
        walletRepository.save(wallet);
        System.out.println("Para cüzdandan başarıyla düşüldü: " + totalAmount + " TL");

        if (simulateCrash) {
            System.out.println("SİSTEM ÇÖKTÜ! Elektrikler kesildi...");
            throw new RuntimeException("Beklenmeyen sistem hatası! Sipariş onaylanamadı.");
        }


        order.markAsPaid(); 
        orderRepository.save(order);

        System.out.println("Sipariş başarıyla onaylandı!");
    }
}