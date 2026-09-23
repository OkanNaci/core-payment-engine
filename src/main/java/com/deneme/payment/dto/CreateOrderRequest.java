package com.deneme.payment.dto;
import jakarta.validation.constraints.NotBlank;

public record CreateOrderRequest(
        @NotBlank(message = "Sipariş numarası boş olamaz")
        String orderId
) {
}