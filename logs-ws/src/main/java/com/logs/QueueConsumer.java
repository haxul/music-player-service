package com.logs;


import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    public void receiveMessage(String message) {
        processMessage(message);
    }
    public void receiveMessage(byte[] message) {
        String strMessage = new String(message);
        processMessage(strMessage);
    }
    private void processMessage(String message) {
        System.out.println(message);
    }
}
