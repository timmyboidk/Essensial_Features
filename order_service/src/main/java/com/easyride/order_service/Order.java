package com.easyride.order_service;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

//Order 类表示订单实体，包含订单的各种属性，如状态、乘客、司机、起点终点位置、预估费用等。
//使用了 @ManyToOne 注解建立与 Passenger 和 Driver 的关联关系。
//使用 @Embedded 和 @AttributeOverrides 将 Location 作为嵌入式对象，分别用于起点和终点位置。

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // 订单状态

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "start_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "start_longitude"))
    })
    private Location startLocation;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "end_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "end_longitude"))
    })
    private Location endLocation;

    private LocalDateTime orderTime; // 下单时间
    private Double estimatedCost; // 预估费用
    private Double estimatedDistance; // 预估距离
    private Double estimatedDuration; // 预估时间
    private String vehicleType; // 车型
    private String serviceType; // 服务类型
    private String paymentMethod; // 支付方式
}


