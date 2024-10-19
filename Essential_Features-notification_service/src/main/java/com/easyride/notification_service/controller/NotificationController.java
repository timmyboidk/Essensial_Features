package com.easyride.notification_service.controller;

import com.easyride.notification_service.model.PushRequest;
import com.easyride.notification_service.service.PushNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final PushNotificationService pushNotificationService;

    public NotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping("/push")
    public ResponseEntity<String> pushNotification(@RequestBody PushRequest pushRequest) {
        pushNotificationService.sendPushNotification(pushRequest.getDeviceToken(), pushRequest.getPayload());
        return ResponseEntity.ok("Notification sent");
    }
}
