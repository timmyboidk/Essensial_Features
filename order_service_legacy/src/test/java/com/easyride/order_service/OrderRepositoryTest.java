package com.easyride.order_service;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Test
    void findByStatus_Success() {
        // 准备数据
        Passenger passenger = new Passenger(1L, "乘客1");
        passengerRepository.save(passenger);

        Order order = new Order();
        order.setStatus("PENDING");
        order.setPassenger(passenger);
        order.setStartLocation(new Location(30.0, 120.0));
        order.setEndLocation(new Location(31.0, 121.0));
        order.setOrderTime(LocalDateTime.now());
        orderRepository.save(order);

        // 执行方法
        List<Order> orders = orderRepository.findByStatus("PENDING");

        // 验证结果
        assertThat(orders).isNotEmpty();
        assertThat(orders.get(0).getStatus()).isEqualTo("PENDING");
    }

    @Test
    void findByDriverIdAndStatus_Success() {
        // 准备数据
        Passenger passenger = new Passenger(1L, "乘客1");
        passengerRepository.save(passenger);

        Driver driver = new Driver(2L, "司机1", "经济型", true);
        driverRepository.save(driver);

        Order order = new Order();
        order.setStatus("ACCEPTED");
        order.setPassenger(passenger);
        order.setDriver(driver);
        order.setStartLocation(new Location(30.0, 120.0));
        order.setEndLocation(new Location(31.0, 121.0));
        order.setOrderTime(LocalDateTime.now());
        orderRepository.save(order);

        // 执行方法
        List<Order> orders = orderRepository.findByDriverIdAndStatus(2L, "ACCEPTED");

        // 验证结果
        assertThat(orders).isNotEmpty();
        assertThat(orders.get(0).getDriver().getId()).isEqualTo(2L);
        assertThat(orders.get(0).getStatus()).isEqualTo("ACCEPTED");
    }
}

