    package com.Pro_Connect.notification_service.consumer;

    import com.Pro_Connect.notification_service.entity.Notification;
    import com.Pro_Connect.notification_service.service.Notificationservice;
    import com.Pro_Connect.PostService.event.PostCreated;
    import com.Pro_Connect.PostService.event.PostLiked;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.kafka.annotation.KafkaListener;
    import org.springframework.stereotype.Service;

    @Service
    @Slf4j
    @RequiredArgsConstructor
    public class PostConsumer {

        private final Notificationservice notificationservice;

        @KafkaListener(topics = "postCreated")
        public void handlePostCreated(PostCreated postCreated) {
            log.info("received PostCreated event, content: {}", postCreated);

            String message=String.format("Your connection with id: %d has created this post: %s",
                    postCreated.getOwnerUserId(),postCreated.getContent());
            Notification notification = Notification.builder()
                    .message(message)
                    .userId(postCreated.getUserID())
                    .build();
            notificationservice.addNotification(notification);
        }

        @KafkaListener(topics = "postLiked")
        public void handlePostLiked(PostLiked postLiked) {
            log.info("received PostLiked event, content: {}", postLiked);
            String message=String.format("User with id: %d has liked your post with id: %d"
                    ,postLiked.getLikedByUserId(),postLiked.getLikedByUserId());
            Notification notification=Notification.builder()
                    .message(message)
                    .userId(postLiked.getOwnerUserId())
                    .build();
            notificationservice.addNotification(notification);
        }

    }
