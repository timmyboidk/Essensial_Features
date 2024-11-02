package com.easyride.order_service;
import jakarta.persistence.*;
import lombok.*;

//Passenger 类表示乘客实体，包含乘客的基本信息。
//id 字段与用户服务中的乘客ID对应。

@Entity
@Table(name = "passengers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    @Id
    private Long id; // 乘客ID，与用户服务的乘客ID对应

    private String name;
}


