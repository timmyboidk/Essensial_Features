package com.easyride.order_service;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private String address;
    private Double latitude;
    private Double longitude;
}

