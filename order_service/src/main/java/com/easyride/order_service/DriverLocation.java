package com.easyride.order_service;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "driver_locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverLocation {

    @Id
    private Long driverId; // 司机ID

    private Double latitude; // 纬度

    private Double longitude; // 经度

    private Long timestamp; // 时间戳
}

