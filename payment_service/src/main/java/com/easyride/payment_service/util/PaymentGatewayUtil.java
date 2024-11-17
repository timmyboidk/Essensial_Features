package com.easyride.payment_service.util;

import com.easyride.payment_service.dto.PaymentRequestDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayUtil {
    public boolean processPayment(PaymentRequestDto paymentRequestDto) {
    return true;
    }
}
