package com.demo.testactivemq.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    @JmsListener(destination = "testQueue",containerFactory = "jmsListenerContainerQueue")
    public void receiveQueue(String text){
        System.out.println("ConsumerQueue接收到的消息为:"+text);
    }
//    @JmsListener(destination = "user1",containerFactory = "jmsListenerContainerQueue")
//    public void user1(String text){
//        System.out.println("user1接收: "+text);
//    }
//    @JmsListener(destination = "user2",containerFactory = "jmsListenerContainerQueue")
//    public void user2(String text){
//        System.out.println("user2接收: "+text);
//    }
}
