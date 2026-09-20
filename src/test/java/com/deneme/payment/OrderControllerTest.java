package com.deneme.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void shouldReturnOrder_WhenOrderExists() throws Exception {
        Order dummyOrder = new Order("TEST-123");
        when(orderService.getOrder("TEST-123")).thenReturn(Optional.of(dummyOrder));

        mockMvc.perform(get("/api/orders/TEST-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("TEST-123"));
    }
    @Test
    public void shouldCreateOrder_WhenValidJsonIsSent() throws Exception {
        String orderJson = """
                {
                    "orderId": "POST-TEST-004",
                    "status": "PENDING"
                }
                """;

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/orders")
                                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                                .content(orderJson)
                )

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value("POST-TEST-004"))
                .andExpect(jsonPath("$.paid").value(false));
    }

    @Test
    public void shouldReturnNoContent_WhenOrderIsDeleted() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/orders/TEST-DELETE-123"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnUpdatedOrder_WhenStatusIsPatched() throws Exception {
        Order updatedOrder = new Order("TEST-PATCH-123");
        updatedOrder.setStatus(OrderStatus.CANCELED);

        when(orderService.getOrder("TEST-PATCH-123")).thenReturn(Optional.of(updatedOrder));


        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch("/api/orders/TEST-PATCH-123/status")
                        .param("status", "CANCELED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELED"));
    }


}