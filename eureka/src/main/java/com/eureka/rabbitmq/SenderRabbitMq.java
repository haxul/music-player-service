package com.eureka.rabbitmq;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SenderRabbitMq {

    private final RabbitTemplate template;
    private final Queue queue;

    public void send() {
        String message = "Hello World!";
        this.template.convertAndSend(queue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
