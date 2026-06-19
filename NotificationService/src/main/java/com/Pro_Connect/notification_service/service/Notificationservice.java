package com.Pro_Connect.notification_service.service;


import com.Pro_Connect.notification_service.entity.Notification;
import com.Pro_Connect.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Notificationservice {

    public final NotificationRepository notificationRepository;

    public void addNotification(Notification notification) {
        log.info("adding Notification to db, content: {}", notification.getMessage());
        notification=notificationRepository.save(notification);
    }

//    sendmailer to send the mail
//    FCM

}
