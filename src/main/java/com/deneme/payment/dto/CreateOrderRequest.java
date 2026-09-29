package com.deneme.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateOrderRequest(

        @NotBlank(message = "Sipariş numarası boş olamaz")
        String orderId,

        @NotNull(message = "Tutar boş olamaz")
        @Positive(message = "Sipariş tutarı kesinlikle 0'dan büyük olmalıdır")
        BigDecimal amount,

        @NotBlank(message = "Para birimi boş olamaz")
        @Size(min = 3, max = 3, message = "Para birimi tam olarak 3 karakter olmalıdır (Örn: TRY, USD)")
        @Pattern(regexp = "^[A-Z]{3}$", message = "Para birimi sadece büyük harflerden oluşmalıdır")
        String currency
) {
}