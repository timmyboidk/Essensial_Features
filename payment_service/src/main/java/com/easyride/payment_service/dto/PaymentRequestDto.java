package com.easyride.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {

    private Long orderId;

    private Long passengerId;

    private Double amount;

    private String paymentMethod; // PAYPAL, CREDIT_CARD, BALANCE

    private String currency; // USD, CNY, etc.

    // 其他支付渠道需要的参数
}

