package com.deneme.payment;

import java.math.BigDecimal;

public interface PaymentMethod {
    boolean processPayment(BigDecimal amount);

    boolean processRefund(BigDecimal amount);
}