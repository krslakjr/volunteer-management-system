package com.example.feedbackservice.event;

import com.example.feedbackservice.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeedbackEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishFeedbackCreatedEvent(FeedbackCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
    }
}

