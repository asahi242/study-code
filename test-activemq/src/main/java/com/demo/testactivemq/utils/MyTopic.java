package com.demo.testactivemq.utils;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Topic;
/**
 * 设置自己的Topic
 */
@Component
public class MyTopic implements Topic {
    private String topicName;

    public void setTopicName(String topicName){
        this.topicName = topicName;
    }
    @Override
    public String getTopicName() throws JMSException {
        return this.topicName;
    }
}
