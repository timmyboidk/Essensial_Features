package com.easyride.payment_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {

    private Long driverId;

    private Double balance;

    private LocalDateTime updatedAt;
}

