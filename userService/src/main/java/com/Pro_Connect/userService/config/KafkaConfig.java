package com.Pro_Connect.userService.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic userCreatedTopic() {
        return new NewTopic("userCreatedTopic", 3, (short) 1);
    }
}
