package com.eureka.security;


import com.eureka.dto.LogAuthenticationDto;
import com.eureka.rabbitmq.QueueProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthenticationEvents {

    private final QueueProducer queueProducer;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) throws Exception {
        LogAuthenticationDto log = createAuthLog(success);
        queueProducer.produce(log);
    }

    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent failures) throws Exception {
        LogAuthenticationDto log = createAuthLog(failures);
        queueProducer.produce(log);
    }

    private <T extends AbstractAuthenticationEvent> LogAuthenticationDto createAuthLog(T attempt) {
        LogAuthenticationDto log = new LogAuthenticationDto();
        log.setDate(new Date());
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) attempt.getSource();
        if (token.getPrincipal().getClass() == String.class) {
            String name = (String) token.getPrincipal();
            log.setUsername(name);
            log.setIsSuccessfulAuthentication(attempt.getAuthentication().isAuthenticated());
        } else {
            User user = (User) token.getPrincipal();
            log.setUsername(user.getUsername());
            log.setIsSuccessfulAuthentication(attempt.getAuthentication().isAuthenticated());
        }
        return log;
    }
}
