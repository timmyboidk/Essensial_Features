package com.easyride.order_service;
import jakarta.persistence.Embeddable;
import lombok.*;

//Location 类表示地理位置，包含经纬度信息。
//使用 @Embeddable 注解，表示该类可以嵌入到其他实体中。

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private Double latitude;
    private Double longitude;
}


