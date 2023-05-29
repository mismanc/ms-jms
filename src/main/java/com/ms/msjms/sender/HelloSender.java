package com.ms.msjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.msjms.config.JMSConfig;
import com.ms.msjms.model.HelloWorldMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public HelloSender(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 4000)
    public void sendMessage(){
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World " + System.currentTimeMillis())
                .build();
        jmsTemplate.convertAndSend(JMSConfig.MY_QUEUE, helloWorldMessage);
    }

    @Scheduled(fixedRate = 4000)
    public void sendAndReceiveMessage() throws JMSException {
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello Send And Receive: " + System.currentTimeMillis())
                .build();
        Message receivedMessage = jmsTemplate.sendAndReceive(JMSConfig.MY_SEND_AND_RECEIVE_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                try {
                    Message helloMessage = session.createTextMessage(objectMapper.writeValueAsString(helloWorldMessage));
                    helloMessage.setStringProperty("_type", "com.ms.msjms.model.HelloWorldMessage");
                    System.out.println("Sending " + helloWorldMessage.getId());
                    return helloMessage;
                } catch (JsonProcessingException e) {
                    throw new JMSException("boom");
                }
            }
        });
        System.out.println("Message Received " + receivedMessage.getBody(String.class));
    }

}
