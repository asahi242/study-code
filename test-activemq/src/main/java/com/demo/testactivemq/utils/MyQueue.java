package com.demo.testactivemq.utils;

import org.springframework.stereotype.Component;
import javax.jms.Queue;

/**
 * 设置自己的Queue
 */
@Component
public class MyQueue implements Queue {
    private String queueName;
    public void setQueueName(String queueName){
        this.queueName = queueName;
    }
    @Override
    public String getQueueName() {
        return this.queueName;
    }
}
