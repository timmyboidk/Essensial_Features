package com.easyride.user_service.kafka;

import com.easyride.user_service.dto.UserEventDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserKafkaProducer {

    private final KafkaTemplate<String, UserEventDto> kafkaTemplate;

    public UserKafkaProducer(KafkaTemplate<String, UserEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserEvent(UserEventDto userEvent) {
        kafkaTemplate.send("user-topic", userEvent);
    }
}
