package com.easyride.payment_service.kafka;

import com.easyride.payment_service.dto.PaymentEventDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventProducer {

    private final KafkaTemplate<String, PaymentEventDto> kafkaTemplate;

    public PaymentEventProducer(KafkaTemplate<String, PaymentEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentEvent(PaymentEventDto paymentEvent) {
        kafkaTemplate.send("payment-topic", paymentEvent);
    }
}
