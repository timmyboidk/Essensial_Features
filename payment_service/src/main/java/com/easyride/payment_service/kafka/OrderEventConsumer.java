package com.easyride.payment_service.kafka;

import com.easyride.payment_service.dto.OrderEventDto;
import com.easyride.payment_service.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    private final PaymentService paymentService;

    public OrderEventConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "order-topic", groupId = "payment-service-group")
    public void listenOrderEvents(OrderEventDto orderEvent) {
        // 根据订单事件执行相应的支付逻辑
        if ("ORDER_COMPLETED".equals(orderEvent.getEventType())) {
            paymentService.processOrderPayment(orderEvent.getOrderId());
        }
        // 处理其他类型的订单事件
    }
}
