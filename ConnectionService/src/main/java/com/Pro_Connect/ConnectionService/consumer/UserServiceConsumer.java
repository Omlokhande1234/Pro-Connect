package com.Pro_Connect.ConnectionService.consumer;

import com.Pro_Connect.ConnectionService.service.PersonService;
import com.Pro_Connect.userService.Event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceConsumer {
    private final PersonService personService;

    @KafkaListener(topics = "userCreatedTopic")
    public void handlePersonCreated(UserCreatedEvent userCreatedEvent) {
        log.info("Received UserCreatedEvent {}", userCreatedEvent);
        personService.createPerson(userCreatedEvent.getUserId(),userCreatedEvent.getName());
    }

}
