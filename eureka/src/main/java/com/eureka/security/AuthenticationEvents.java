package com.eureka.security;


import com.eureka.rabbitmq.QueueProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationEvents {

    private final QueueProducer queueProducer;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) throws Exception {
        System.out.println("GOOD");
        queueProducer.produce("Hello from users-ws");
    }

    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent failures) {
        System.out.println("BAD");
    }
}
