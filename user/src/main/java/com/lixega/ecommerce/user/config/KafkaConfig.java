package com.lixega.ecommerce.user.config;

import com.lixega.ecommerce.sdk.core.model.dto.UserProfileCreationRequest;
import com.lixega.ecommerce.user.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@AllArgsConstructor
public class KafkaConfig {
    private final UserProfileService userProfileService;

    @Bean
    public NewTopic topic(){
        return TopicBuilder.name("ecommerce-user-creation")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @KafkaListener(id = "userProfileCreation", topics = "ecommerce-user-creation")
    public void listenForUserProfileCreation(UserProfileCreationRequest request){
        userProfileService.createUserProfile(request);
    }
}
