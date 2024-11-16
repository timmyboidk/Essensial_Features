package com.easyride.order_service.kafka;

import com.easyride.order_service.dto.UserEventDto;
import com.easyride.order_service.model.User;
import com.easyride.order_service.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserKafkaConsumer {

    private final UserRepository userRepository;

    public UserKafkaConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "user-topic", groupId = "order-service-group")
    public void consume(UserEventDto userEvent) {
        // Save or update user in local database
        User user = new User();
        user.setId(userEvent.getId());
        user.setUsername(userEvent.getUsername());
        user.setEmail(userEvent.getEmail());
        user.setRole(userEvent.getRole());

        userRepository.save(user);
    }
}
