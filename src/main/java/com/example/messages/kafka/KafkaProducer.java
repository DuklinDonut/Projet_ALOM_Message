package com.example.messages.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.messages.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.TokenServiceClient;

@Component
public class KafkaProducer {

    private static final String TOPIC = "conversations";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenServiceClient tokenServiceClient; // Injecter le client HTTP

    public void sendMessage(Message message) throws JsonProcessingException {
        // Appeler le TokenService pour obtenir le username
        String username = tokenServiceClient.getUsernameFromToken(message.getReceiverId());

        // Mettre à jour le ReceiverId avec le username
        message.setReceiverId(username);

        // Convertir le message en JSON
        String messageJson = objectMapper.writeValueAsString(message);

        // Envoyer le message dans Kafka
        kafkaTemplate.send(TOPIC, username, messageJson);
    }
}
