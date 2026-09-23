package com.deneme.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RevenueResponse(
        BigDecimal totalRevenue,
        String currency,
        LocalDateTime calculatedAt
) {
}