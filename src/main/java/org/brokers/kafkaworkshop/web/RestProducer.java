package org.brokers.kafkaworkshop.web;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class RestProducer {
    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final String topic;

    public RestProducer(KafkaTemplate<Integer, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = "test-topic";
    }

    @GetMapping("/publish/{message}")
    public String publishMessage(@PathVariable String message) {
        kafkaTemplate.send(topic, message);

        return "Message Published!";
    }
}
