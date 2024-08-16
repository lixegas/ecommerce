package com.lixega.ecommerce.user.config;

import com.lixega.ecommerce.user.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@AllArgsConstructor
public class KafkaConfig {

    private final UserService userService;

    @Bean
    public NewTopic topic(){
        return TopicBuilder.name("ecommerce-user-registration")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @KafkaListener(id = "userProfileCreation", topics = "ecommerce-user-registration")
    public void listenForUserProfileCreation(String userId){
        System.out.println(userId);
        userService.createUserProfile(userId);
    }
}
