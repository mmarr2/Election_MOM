package com.example.kafka.consumer;

import com.example.election.ElectionData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "election", groupId = "1")
    public void listen(ElectionData electionData) {
        System.out.println("Received message: " + electionData.toString());
    }

}