package com.easyride.order_service;
import jakarta.persistence.*;
import lombok.*;

//Driver 类表示司机实体，包含司机的基本信息。
//id 字段与用户服务中的司机ID对应。
//isAvailable 表示司机是否空闲，可用于订单匹配。

@Entity
@Table(name = "drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    private Long id; // 司机ID，与用户服务的司机ID对应

    private String name;
    private String vehicleType;
    private Boolean isAvailable;
}

