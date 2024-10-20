package com.easyride.order_service;
import lombok.*;
import java.time.LocalDateTime;
//OrderResponseDto 用于返回订单的详细信息。
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Long orderId;
    private String status;
    private Long passengerId;
    private Long driverId;
    private Location startLocation;
    private Location endLocation;
    private LocalDateTime orderTime;
    private Double estimatedCost;
    private Double estimatedDistance;
    private Double estimatedDuration;
    private String vehicleType;
    private String serviceType;
    private String paymentMethod;
}

