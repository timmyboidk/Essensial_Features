package com.easyride.payment_service.service;

import com.easyride.payment_service.dto.PaymentRequestDto;
import com.easyride.payment_service.dto.PaymentResponseDto;
import com.easyride.payment_service.model.Payment;
import com.easyride.payment_service.model.PaymentStatus;
import com.easyride.payment_service.model.TransactionType;
import com.easyride.payment_service.repository.PaymentRepository;
import com.easyride.payment_service.util.PaymentGatewayUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final WalletService walletService;
    private final PaymentGatewayUtil paymentGatewayUtil;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              WalletService walletService,
                              PaymentGatewayUtil paymentGatewayUtil) {
        this.paymentRepository = paymentRepository;
        this.walletService = walletService;
        this.paymentGatewayUtil = paymentGatewayUtil;
    }

    @Override
    public PaymentResponseDto processPayment(PaymentRequestDto paymentRequestDto) {
        // 创建支付记录
        Payment payment = new Payment();
        payment.setOrderId(paymentRequestDto.getOrderId());
        payment.setPassengerId(paymentRequestDto.getPassengerId());
        payment.setAmount(paymentRequestDto.getAmount());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTransactionType(TransactionType.PAYMENT);
        payment.setCreatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        // 调用支付网关进行支付
        boolean paymentResult = paymentGatewayUtil.processPayment(paymentRequestDto);

        if (paymentResult) {
            payment.setStatus(PaymentStatus.COMPLETED);
            payment.setUpdatedAt(LocalDateTime.now());
            paymentRepository.save(payment);

            // 更新司机钱包余额
            walletService.addFunds(paymentRequestDto.getOrderId(), paymentRequestDto.getAmount());

            return new PaymentResponseDto(payment.getId(), "COMPLETED", "支付成功");
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setUpdatedAt(LocalDateTime.now());
            paymentRepository.save(payment);

            return new PaymentResponseDto(payment.getId(), "FAILED", "支付失败");
        }
    }

    @Override
    public void handlePaymentNotification(Map<String, String> notificationData) {
        // 处理支付网关的异步通知，更新支付状态
    }

    @Override
    public void refundPayment(Long paymentId, Double amount) {
        // 实现退款逻辑，调用支付网关的退款接口
    }
}

