package com.easyride.payment_service.controller;

import com.easyride.payment_service.dto.PaymentRequestDto;
import com.easyride.payment_service.dto.PaymentResponseDto;
import com.easyride.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // 发起支付请求
    @PostMapping("/pay")
    public PaymentResponseDto processPayment(@Valid @RequestBody PaymentRequestDto paymentRequestDto) {
        return paymentService.processPayment(paymentRequestDto);
    }

    // 接收支付网关的异步通知
    @PostMapping("/notify")
    public void handlePaymentNotification(@RequestBody Map<String, String> notificationData) {
        paymentService.handlePaymentNotification(notificationData);
    }

    // 退款请求
    @PostMapping("/refund/{paymentId}")
    public void refundPayment(@PathVariable Long paymentId, @RequestParam Double amount) {
        paymentService.refundPayment(paymentId, amount);
    }
}

