package com.eureka.security;


import com.eureka.rabbitmq.SenderRabbitMq;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationEvents {

    private final SenderRabbitMq senderRabbitMq;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        senderRabbitMq.send();
        System.out.println("GOOD");
    }

    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent failures) {
        System.out.println("BAD");
    }
}
