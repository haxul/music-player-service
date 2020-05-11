package com.logs.rabbitmq;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logs.dto.LogAuthenticationDto;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    public void receiveMessage(String message) throws JsonProcessingException {
        LogAuthenticationDto log = new ObjectMapper().readValue(message, LogAuthenticationDto.class);
        System.out.println(log);
    }


}
