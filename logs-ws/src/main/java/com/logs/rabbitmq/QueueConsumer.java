package com.logs.rabbitmq;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logs.dto.LogAuthenticationDto;
import com.logs.services.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@Component
@RequiredArgsConstructor
public class QueueConsumer {

    private final LogService logService;

    public void receiveMessage(String message) throws IOException {
        LogAuthenticationDto log = new ObjectMapper().readValue(message, LogAuthenticationDto.class);
        logService.addLine(log);
    }
}
