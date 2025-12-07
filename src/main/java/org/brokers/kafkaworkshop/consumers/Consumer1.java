package org.brokers.kafkaworkshop.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer1 {
    @KafkaListener(topics = "test-topic", groupId = "group1")
    public void onMessage(String message) {
        System.out.println("Received Message: " + message);
    }
}
