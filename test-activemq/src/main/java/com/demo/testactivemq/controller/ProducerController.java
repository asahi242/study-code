package com.demo.testactivemq.controller;

import com.demo.testactivemq.utils.MyQueue;
import com.demo.testactivemq.utils.MyTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import java.util.HashMap;

@RestController
public class ProducerController {
    //Queue模式
    @Autowired
    private MyQueue queue;
    //Topic
    @Autowired
    private MyTopic topic;
    //注入springboot封装的工具类
    @Autowired
    private JmsMessagingTemplate jms;

    @GetMapping("sendMessage")
    public String sendMessage(String queueName) throws JMSException {
        queue.setQueueName(queueName);
        //发送消息至消息中间件代理(Borker)
        jms.convertAndSend(queue,"testQueue");
        return "queue success";
    }

//    @PostMapping("sendMessage1")
//    public String sendMessage1(@RequestBody HashMap<String ,String> map) throws JMSException {
//        String from = map.get("from");
//        String to = map.get("to");
//        String content = map.get("content");
//        queue.setQueueName(to);
//        //发送消息至消息中间件代理(Borker)
//        jms.convertAndSend(queue,from+": "+content);
//        return from+": "+content;
//    }

    /**
     * 订阅模式（topic）发送消息
     *
     * @return
     */
    @GetMapping("/topicSend")
    public String topicSend(String topicName) {
        topic.setTopicName(topicName);
        jms.convertAndSend(topic, "testTopic");
        return "topic success";
    }
}
