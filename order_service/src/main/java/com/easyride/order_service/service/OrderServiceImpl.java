package com.easyride.order_service.service;

import com.easyride.order_service.dto.*;
import com.easyride.order_service.model.*;
import com.easyride.order_service.repository.*;
import com.easyride.order_service.util.DistanceCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public  class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            PassengerRepository passengerRepository,
                            DriverRepository driverRepository) {
        this.orderRepository = orderRepository;
        this.passengerRepository = passengerRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderCreateDto orderCreateDto) {
        // 与之前相同的实现
        return null;
    }

    @Override
    @Transactional
    public void acceptOrder(Long orderId, Long driverId) {
        // 与之前相同的实现
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));

        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setOrderId(order.getId());
        responseDto.setStatus(order.getStatus());
        responseDto.setPassengerName(order.getPassenger().getName());
        responseDto.setDriverName(order.getDriver() != null ? order.getDriver().getName() : null);
        responseDto.setEstimatedCost(order.getEstimatedCost());
        responseDto.setEstimatedDistance(order.getEstimatedDistance());
        responseDto.setEstimatedDuration(order.getEstimatedDuration());

        return responseDto;
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);

        // 如果订单已分配司机，更新司机状态为可用
        if (order.getDriver() != null) {
            Driver driver = order.getDriver();
            driver.setAvailable(true);
            driverRepository.save(driver);
        }
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {

    }

    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        return null;
    }
}
