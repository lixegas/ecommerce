package com.lixega.ecommerce.registrationeventlistener.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lixega.ecommerce.registrationeventlistener.config.KafkaConstants;
import com.lixega.ecommerce.registrationeventlistener.config.KafkaProducerCreator;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Slf4j
public class UserRegistrationEventListener implements EventListenerProvider {
    public final static String ID = "user-registration-listener";

    private final RealmProvider model;

    private final Producer<String, String> userIdProducer =
            KafkaProducerCreator.createUserModelProducer();

    public UserRegistrationEventListener(RealmProvider model) {
        this.model = model;
    }

    @Override
    public void onEvent(Event event) {
        String eventJson;
        try {
            eventJson = new ObjectMapper().writeValueAsString(event);
        } catch (JsonProcessingException e) {
            eventJson = "";
        }

        log.info(String.format("Recieved event %s", eventJson));
        if (EventType.REGISTER.equals(event.getType())) {
            RealmModel realm = this.model.getRealm(event.getRealmId());
            if (!Objects.equals(realm.getName(), "lixega-ecommerce")) {
                return;
            }

            try {
                sendData(event.getUserId());
            } catch (ExecutionException | InterruptedException e) {
                log.debug(String.format("Error while sending %s", event.getUserId()));
            }
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
    }

    private void sendData(String userId) throws ExecutionException, InterruptedException {
        log.debug(String.format("Sending userId %s to channel %s", userId, KafkaConstants.TOPIC_NAME));
        ProducerRecord<String, String> record =
                new ProducerRecord<String, String>(KafkaConstants.TOPIC_NAME, userId);
        userIdProducer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(e != null){
                    log.error("Failed to send message");
                }
                log.debug(String.format("Sent userId %s to channel %s", userId, KafkaConstants.TOPIC_NAME));
            }
        }).get();
    }

    @Override
    public void close() {
        userIdProducer.close();
    }
}
