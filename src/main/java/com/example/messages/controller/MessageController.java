package com.example.messages.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.messages.kafka.KafkaProducer;
import com.example.messages.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping("/send")
    public Message sendMessage(@RequestBody Message message) throws JsonProcessingException {
        kafkaProducer.sendMessage(message);
        message.setContent(message.getContent() + " - sent");
        return message;
    }
}
