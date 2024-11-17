package com.easyride.payment_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalRequestDto {

    private Long driverId;

    private Double amount;

    private String bankAccount;
}

