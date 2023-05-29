package com.ms.msjms.sender;

import com.ms.msjms.config.JMSConfig;
import com.ms.msjms.model.HelloWorldMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;

    public HelloSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Scheduled(fixedRate = 4000)
    public void sendMessage(){
        System.out.println("I'm Sending Message");
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World " + System.currentTimeMillis())
                .build();
        jmsTemplate.convertAndSend(JMSConfig.MY_QUEUE, helloWorldMessage);
        System.out.println("Message Send " + helloWorldMessage.getId());
    }

}
