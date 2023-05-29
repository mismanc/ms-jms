package com.ms.msjms.listener;

import com.ms.msjms.config.JMSConfig;
import com.ms.msjms.model.HelloWorldMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    public HelloMessageListener(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = JMSConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message) {
        System.out.println("I Got the Message");
        System.out.println(helloWorldMessage);
    }

    @JmsListener(destination = JMSConfig.MY_SEND_AND_RECEIVE_QUEUE)
    public void listenForReceive(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers,
                                 Message message) throws JMSException {
        HelloWorldMessage worldMessage = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("World " + System.currentTimeMillis())
                .build();
        jmsTemplate.convertAndSend(message.getJMSReplyTo(), worldMessage);
    }


}
