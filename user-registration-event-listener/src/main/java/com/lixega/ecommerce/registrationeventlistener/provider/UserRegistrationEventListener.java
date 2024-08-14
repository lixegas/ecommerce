package com.lixega.ecommerce.registrationeventlistener.provider;

import com.lixega.ecommerce.registrationeventlistener.config.KafkaConstants;
import com.lixega.ecommerce.registrationeventlistener.config.KafkaProducerCreator;
import org.apache.kafka.clients.producer.*;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

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
        if (EventType.REGISTER.equals(event.getType())) {
            RealmModel realm = this.model.getRealm(event.getRealmId());
            if (!Objects.equals(realm.getName(), "lixega-ecommerce")) {
                return;
            }

            try {
                sendData(event.getUserId());
            } catch (ExecutionException | InterruptedException e) {
                System.out.println("Error while sending userId");
            }
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
    }

    private void sendData(String userId) throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record =
                new ProducerRecord<String, String>(KafkaConstants.TOPIC_NAME, userId);
        userIdProducer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(e != null){
                    System.out.println("Failed to send message");
                }
            }
        }).get();
    }

    @Override
    public void close() {
        userIdProducer.close();
    }
}
