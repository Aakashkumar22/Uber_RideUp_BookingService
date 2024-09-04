package com.example.uberbokingservice.Consumers;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class kafkaConsumerSevice {
    @KafkaListener(topics = "sample-topic")

    public void listen(String message){
        System.out.println("Listing kafka message from client in Booking service: "+message);
    }
}
