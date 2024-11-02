package com.easyride.order_service;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    @NotNull
    private Long passengerId;

    @NotNull
    private Location startLocation;

    @NotNull
    private Location endLocation;

    @NotNull
    private String vehicleType;

    @NotNull
    private String serviceType;

    @NotNull
    private String paymentMethod;
}

