package org.brokers.kafkaworkshop;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@EnableKafka
public class KafkaWorkshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaWorkshopApplication.class, args);
    }

//    @Bean
    public CommandLineRunner commandLineRunner() {
        AtomicInteger counter = new AtomicInteger();
        return args -> {
            String BROKER_URL = "broker1:9092,broker2:9092,broker3:9092";
            String TOPIC = "test-topic";
            String GROUP_ID = "group1";

            Properties props = new Properties();
            props.put("bootstrap.servers", BROKER_URL);
            props.put("group.id", GROUP_ID);
            props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);
            Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
                String msg = String.valueOf(Math.random() * 1000);
                ProducerRecord<Integer, String> record = new ProducerRecord<Integer, String>(TOPIC, counter.incrementAndGet(), msg);
                producer.send(record, (recordMetadata, e) -> {
                    System.out.println("Sending Message Key => " + counter + "Value => " + msg);
                    System.out.println("Partition => " + recordMetadata.partition() + "Offset => " + recordMetadata.offset());
                });
            }, 1000, 1000, TimeUnit.MILLISECONDS);
        };
    }
}
