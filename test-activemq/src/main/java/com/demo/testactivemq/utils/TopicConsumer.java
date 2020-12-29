package com.demo.testactivemq.utils;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {
    @JmsListener(destination = "springBootTopic",containerFactory = "jmsListenerContainerTopic")
    public void receiveTopic(String text){
        System.out.println("ConsumerTopic接收到的消息为:"+text);
    }
}
