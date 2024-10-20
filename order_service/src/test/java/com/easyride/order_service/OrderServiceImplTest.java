package com.easyride.order_service;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private DriverRepository driverRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_Success() {
        // 准备数据
        Long passengerId = 1L;
        Passenger passenger = new Passenger(passengerId, "乘客1");
        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));

        OrderRequestDto orderRequest = new OrderRequestDto(
                passengerId,
                new Location(30.0, 120.0),
                new Location(31.0, 121.0),
                "经济型",
                "快车",
                "在线支付"
        );

        // 模拟保存订单
        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setStatus("PENDING");
        savedOrder.setPassenger(passenger);
        savedOrder.setStartLocation(orderRequest.getStartLocation());
        savedOrder.setEndLocation(orderRequest.getEndLocation());
        savedOrder.setOrderTime(LocalDateTime.now());
        savedOrder.setEstimatedCost(50.0);
        savedOrder.setEstimatedDistance(10.0);
        savedOrder.setEstimatedDuration(15.0);
        savedOrder.setVehicleType(orderRequest.getVehicleType());
        savedOrder.setServiceType(orderRequest.getServiceType());
        savedOrder.setPaymentMethod(orderRequest.getPaymentMethod());

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // 执行方法
        OrderResponseDto responseDto = orderService.createOrder(orderRequest);

        // 验证结果
        assertNotNull(responseDto);
        assertEquals("PENDING", responseDto.getStatus());
        assertEquals(passengerId, responseDto.getPassengerId());
        assertNull(responseDto.getDriverId());
        assertEquals(orderRequest.getVehicleType(), responseDto.getVehicleType());
    }

    @Test
    void acceptOrder_Success() {
        // 准备数据
        Long orderId = 1L;
        Long driverId = 2L;

        Order order = new Order();
        order.setId(orderId);
        order.setStatus("PENDING");

        Driver driver = new Driver(driverId, "司机1", "经济型", true);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(driverRepository.save(any(Driver.class))).thenReturn(driver);

        // 执行方法
        orderService.acceptOrder(orderId, driverId);

        // 验证结果
        assertEquals("ACCEPTED", order.getStatus());
        assertEquals(driverId, order.getDriver().getId());
        assertFalse(driver.getIsAvailable());
    }

    @Test
    void updateOrderStatus_Completed() {
        // 准备数据
        Long orderId = 1L;
        String status = "COMPLETED";

        Driver driver = new Driver(2L, "司机1", "经济型", false);

        Order order = new Order();
        order.setId(orderId);
        order.setStatus("IN_PROGRESS");
        order.setDriver(driver);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(driverRepository.save(any(Driver.class))).thenReturn(driver);

        // 执行方法
        orderService.updateOrderStatus(orderId, status);

        // 验证结果
        assertEquals(status, order.getStatus());
        assertTrue(driver.getIsAvailable());
    }

    @Test
    void getOrderById_Success() {
        // 准备数据
        Long orderId = 1L;
        Passenger passenger = new Passenger(1L, "乘客1");
        Driver driver = new Driver(2L, "司机1", "经济型", false);

        Order order = new Order();
        order.setId(orderId);
        order.setStatus("IN_PROGRESS");
        order.setPassenger(passenger);
        order.setDriver(driver);
        order.setStartLocation(new Location(30.0, 120.0));
        order.setEndLocation(new Location(31.0, 121.0));
        order.setOrderTime(LocalDateTime.now());
        order.setEstimatedCost(50.0);
        order.setEstimatedDistance(10.0);
        order.setEstimatedDuration(15.0);
        order.setVehicleType("经济型");
        order.setServiceType("快车");
        order.setPaymentMethod("在线支付");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // 执行方法
        OrderResponseDto responseDto = orderService.getOrderById(orderId);

        // 验证结果
        assertNotNull(responseDto);
        assertEquals(orderId, responseDto.getOrderId());
        assertEquals("IN_PROGRESS", responseDto.getStatus());
        assertEquals(passenger.getId(), responseDto.getPassengerId());
        assertEquals(driver.getId(), responseDto.getDriverId());
    }
}



