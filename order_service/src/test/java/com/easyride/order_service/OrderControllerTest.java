package com.easyride.order_service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void createOrder_Success() throws Exception {
        // 准备数据
        OrderRequestDto orderRequest = new OrderRequestDto(
                1L,
                new com.easyride.order_service.Location(30.0, 120.0),
                new com.easyride.order_service.Location(31.0, 121.0),
                "经济型",
                "快车",
                "在线支付"
        );

        OrderResponseDto orderResponse = new OrderResponseDto(
                1L,
                "PENDING",
                1L,
                null,
                orderRequest.getStartLocation(),
                orderRequest.getEndLocation(),
                java.time.LocalDateTime.now(),
                50.0,
                10.0,
                15.0,
                orderRequest.getVehicleType(),
                orderRequest.getServiceType(),
                orderRequest.getPaymentMethod()
        );

        when(orderService.createOrder(any(OrderRequestDto.class))).thenReturn(orderResponse);

        // 执行并断言
        mockMvc.perform(post("/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.passengerId").value(1));
    }

    @Test
    void acceptOrder_Success() throws Exception {
        // 准备数据
        doNothing().when(orderService).acceptOrder(1L, 2L);

        // 执行并断言
        mockMvc.perform(post("/orders/1/accept")
                        .param("driverId", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("订单已接单"));
    }

    @Test
    void updateOrderStatus_Success() throws Exception {
        // 准备数据
        doNothing().when(orderService).updateOrderStatus(1L, "COMPLETED");

        // 执行并断言
        mockMvc.perform(put("/orders/1/status")
                        .param("status", "COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(content().string("订单状态已更新"));
    }

    @Test
    void getOrderById_Success() throws Exception {
        // 准备数据
        OrderResponseDto orderResponse = new OrderResponseDto(
                1L,
                "IN_PROGRESS",
                1L,
                2L,
                new com.easyride.order_service.Location(30.0, 120.0),
                new com.easyride.order_service.Location(31.0, 121.0),
                java.time.LocalDateTime.now(),
                50.0,
                10.0,
                15.0,
                "经济型",
                "快车",
                "在线支付"
        );

        when(orderService.getOrderById(1L)).thenReturn(orderResponse);

        // 执行并断言
        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.passengerId").value(1))
                .andExpect(jsonPath("$.driverId").value(2));
    }
}

