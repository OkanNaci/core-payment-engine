package com.deneme.payment;

import com.deneme.payment.dto.CreateOrderRequest;
import com.deneme.payment.dto.OrderResponse;
import com.deneme.payment.dto.RevenueResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void shouldReturnOrder_WhenOrderExists() throws Exception {
        OrderResponse mockResponse = new OrderResponse("TEST-123", "PENDING", BigDecimal.ZERO);

        when(orderService.getOrder("TEST-123")).thenReturn(mockResponse);

        mockMvc.perform(get("/api/orders/TEST-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("TEST-123"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    public void shouldCreateOrder_WhenValidJsonIsSent() throws Exception {
        String orderJson = """
                {
                    "orderId": "POST-TEST-004"
                }
                """;

        OrderResponse mockResponse = new OrderResponse("POST-TEST-004", "PENDING", BigDecimal.ZERO);

        when(orderService.processNewOrder(any(CreateOrderRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value("POST-TEST-004"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    public void shouldReturnNoContent_WhenOrderIsDeleted() throws Exception {
        mockMvc.perform(delete("/api/orders/TEST-DELETE-123"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnUpdatedOrder_WhenStatusIsPatched() throws Exception {
        OrderResponse mockResponse = new OrderResponse("TEST-PATCH-123", "CANCELED", BigDecimal.ZERO);

        when(orderService.getOrder("TEST-PATCH-123")).thenReturn(mockResponse);

        mockMvc.perform(patch("/api/orders/TEST-PATCH-123/status")
                        .param("status", "CANCELED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELED"));
    }

    @Test
    public void shouldReturnRevenueResponse_WhenGetRevenueIsCalled() throws Exception {
        RevenueResponse mockResponse = new RevenueResponse(
                new BigDecimal("250.50"),
                "TRY",
                LocalDateTime.now()
        );

        when(orderService.getDailyRevenue()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/orders/revenue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRevenue").value(250.50))
                .andExpect(jsonPath("$.currency").value("TRY"))
                .andExpect(jsonPath("$.calculatedAt").exists());
    }
}