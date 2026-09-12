package com.main.kafka;

import com.main.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @KafkaListener(
            topics = "order-created",
            groupId = "order-service-group"
    )
    public void consumeOrderCreated(OrderCreatedEvent event) {

        System.out.println(
                "Received OrderCreated event: " + event
        );
    }
}