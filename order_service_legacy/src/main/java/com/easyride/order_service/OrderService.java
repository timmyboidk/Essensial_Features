package com.easyride.order_service;
//OrderService 接口定义了订单相关的业务逻辑方法，包括创建订单、接受订单、更新订单状态和获取订单详情。
public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequest);

    void acceptOrder(Long orderId, Long driverId);

    void updateOrderStatus(Long orderId, String status);

    OrderResponseDto getOrderById(Long orderId);
}


